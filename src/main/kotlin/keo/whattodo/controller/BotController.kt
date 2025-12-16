package keo.whattodo.controller

import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import org.springframework.stereotype.Component

@Component
class BotController(private val kord: Kord) {

    @PostConstruct
    fun startListening() {
        kord.on<MessageCreateEvent> {
            if (message.author?.isBot == true) return@on

            if (message.content == "!대화") {
                val channel = message.channel
                val authorId = message.author?.id ?: return@on
                val targetChannelId = message.channelId

                channel.createMessage("어떤 말을 하고 싶어?")

                val responseEvent = withTimeoutOrNull(30000) {
                    kord.events
                        .filterIsInstance<MessageCreateEvent>() // 1. 메시지 이벤트만 골라냄
                        .filter { event ->                      // 2. 조건 확인
                            event.message.author?.id == authorId &&
                                    event.message.channelId == targetChannelId
                        }
                        .first() // 3. 조건에 맞는 첫 번째 이벤트가 올 때까지 대기 (Suspend)
                }

                if (responseEvent != null) {
                    val userResponse = responseEvent.message.content
                    channel.createMessage("너는 \"$userResponse\"라고 말했어")
                } else {
                    channel.createMessage("시간이 초과되어서 대화가 종료됐어. ⏰")
                }
            }
        }
    }
}