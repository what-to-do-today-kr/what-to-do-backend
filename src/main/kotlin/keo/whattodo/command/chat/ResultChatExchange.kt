package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import org.springframework.stereotype.Component

@Component
class ResultChatExchange : ChatExchange {
    override val order: ChatOrder = ChatOrder.RESULT

    override fun askQuestion(): ChatResponse {
        return ChatResponse("결과는 추후 구현 예정입니다.", emptyList())
    }

    override fun answer(message: String): ChatResponse {
        return ChatResponse()
    }
}
