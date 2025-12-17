package keo.whattodo.controller

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.asChannelOf
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.entity.channel.thread.ThreadChannel
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component

@Component
class DiscordBotController(private val kord: Kord) {

    private val TEST_GUILD_ID = Snowflake(401976520425472020)
    private val CHAT_INPUT_COMMAND = "오늘-뭐-하지"

    @PostConstruct
    fun startListening() {
        kord.launch {
            registerCommands()
        }
        handleInteractions() // 명령어 처리 (스레드 생성)
        handleThreadMessages() // 스레드 내 대화 처리
    }

    private suspend fun registerCommands() {
        kord.createGuildChatInputCommand(TEST_GUILD_ID, CHAT_INPUT_COMMAND, "오늘 뭐 할지 물어보자")
    }

    private fun handleInteractions() {
        kord.on<GuildChatInputCommandInteractionCreateEvent> {
            val command = interaction.command

            if (command.rootName == CHAT_INPUT_COMMAND) {
                val topic = command.strings["message"] ?: return@on // 수정 필요

                // 1. 응답 메시지 보내기
                val responseBehavior = interaction.deferPublicResponse()
                val responseMessage = responseBehavior.respond {
                    content = "주제: **$topic** 에 대한 대화방을 만들게! 🧵"
                }

                // 2. ⭐ [변경됨] 채널을 가져와서 TextChannel로 변환합니다.
                // (스레드는 일반 텍스트 채널에서만 생성이 가능하기 때문에 명시해줍니다)
                val channel = interaction.getChannel().asChannelOf<TextChannel>()

                // 3. ⭐ [변경됨] "이 메시지 ID(responseMessage.message.id)로 스레드를 만들어줘" 라고 요청
                val thread = channel.startPublicThreadWithMessage(
                    messageId = responseMessage.message.id,
                    name = "'$topic' 토론방",
                )
                thread.createMessage("여기서 대화를 이어나가자! 어떤 점이 궁금해?")
            }
        }
    }

    // 2. 스레드 안에서 유저가 말하면 대답해줍니다.
    private fun handleThreadMessages() {
        kord.on<MessageCreateEvent> {
            // 봇 자신의 메시지는 무시
            if (message.author?.isBot == true) return@on

            // 메시지가 온 채널을 가져옵니다.
            val channel = message.getChannel()

            // 이 채널이 '스레드(ThreadChannel)'인지 확인합니다.
            // (일반 채팅방에서 치는 건 무시하고, 스레드 안에서만 반응하게 하려는 의도)
            if (channel is ThreadChannel) {

                // 필요하다면 특정 이름의 스레드에서만 동작하게 할 수도 있습니다.
                // if (channel.name.contains("토론방")) { ... }

                // 스레드에 답장 보내기
                channel.createMessage("네가 스레드에서 말했구나: ${message.content}")
            }
        }
    }
}
