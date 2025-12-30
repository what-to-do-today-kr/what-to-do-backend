package keo.whattodo.controller

import dev.kord.common.entity.ButtonStyle
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.asChannelOf
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.behavior.interaction.updatePublicMessage
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.entity.channel.thread.ThreadChannel
import dev.kord.core.event.interaction.ButtonInteractionCreateEvent
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.message.actionRow
import jakarta.annotation.PostConstruct
import keo.whattodo.command.ChatContext
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.ChatResponse
import keo.whattodo.command.StartChat
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import org.springframework.stereotype.Component

@Component
class DiscordBotController(
    private val kord: Kord,
    private val startChat: StartChat,
    private val chatContext: ChatContext,
) {
    private val testGuildId = Snowflake(401976520425472020)
    private val chatInputCommand = "오늘-뭐-하지"
    private val platform = "DISCORD"

    @PostConstruct
    fun startListening() {
        kord.launch {
            kord.createGuildChatInputCommand(testGuildId, chatInputCommand, "오늘 뭐 할지 물어보자")
        }
        handleInteractions()
        handleButtonEvent()
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun handleInteractions() {
        kord.events
            .filterIsInstance<GuildChatInputCommandInteractionCreateEvent>()
            .filter { event ->
                if (event.interaction.user.isBot) return@filter false
                if (event.interaction.command.rootName != chatInputCommand) return@filter false
                return@filter true
            }
            .onEach { event ->
                if (event.interaction.getChannel() !is TextChannel) {
                    event.interaction.deferEphemeralResponse().respond {
                        content = "이 명령어는 텍스트 채널에서만 사용할 수 있습니다."
                    }
                    return@onEach
                }

                val userName: String = event.interaction.user.effectiveName
                val date: String = Clock.System.now()
                    .toLocalDateTime(TimeZone.of("Asia/Seoul"))
                    .format(LocalDateTime.Format { byUnicodePattern("yyyy/MM/dd") })
                val responseMessage = "${userName}의 $date 할 일 추천"

                val respond = event.interaction.deferPublicResponse().respond { content = responseMessage }
                val channel = event.interaction.getChannel().asChannelOf<TextChannel>()

                val threadChannel = channel.startPublicThreadWithMessage(
                    messageId = respond.message.id,
                    name = responseMessage,
                )

                val roomId = threadChannel.id.toString()
                val userId = event.interaction.user.id.toString()
                val chatId = startChat.startChat(platform, userId, roomId)

                val response = chatContext.getFirstChat().askQuestion()
                sendMessage(threadChannel, ChatOrder.getStart(), response, chatId)
            }
            .launchIn(kord)
    }

    private fun handleButtonEvent() {
        kord.events
            .filterIsInstance<ButtonInteractionCreateEvent>()
            .filter { event ->
                val author = event.interaction.message.author ?: return@filter false
                if (!author.isBot) return@filter false
                if (event.interaction.getChannel() !is ThreadChannel) return@filter false
                if (event.interaction.componentId.split("_").size != 3) return@filter false

                val threadChannel = event.interaction.message.getChannel().asChannelOf<ThreadChannel>()
                return@filter threadChannel.ownerId == kord.selfId
            }
            .onEach { event ->
                val channel = event.interaction.message.getChannel().asChannelOf<ThreadChannel>()
                event.interaction.updatePublicMessage { components = mutableListOf() }

                val componentId = event.interaction.componentId.split("_")
                val chatId = componentId[0].toLong()
                val order = ChatOrder.fromString(componentId[1])
                val userInput = componentId[2]

                val command = chatContext.getCurrentChat(order)
                val answer = command.answer(userInput, chatId)
                sendMessage(channel, order, answer, chatId)

                val nextCommand = chatContext.getNextChat(command)
                val question = nextCommand.askQuestion()
                sendMessage(channel, order.next, question, chatId)
            }
            .launchIn(kord)
    }

    private suspend fun sendMessage(channel: MessageChannel, order: ChatOrder, response: ChatResponse, chatId: Long) {
        if (response.message.isEmpty()) return

        channel.createMessage {
            content = response.message
            if (response.choices.isNotEmpty()) {
                actionRow {
                    response.choices.forEach { choice ->
                        val customId = chatId.toString() + "_" + order.name + "_" + choice.response
                        interactionButton(ButtonStyle.Primary, customId) { label = choice.description }
                    }
                }
            }
        }
    }
}
