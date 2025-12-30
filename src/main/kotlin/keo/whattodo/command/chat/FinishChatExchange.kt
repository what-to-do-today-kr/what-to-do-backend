package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.ChatResponse
import org.springframework.stereotype.Component

@Component
class FinishChatExchange : ChatExchange {
    override val order: ChatOrder = ChatOrder.FINISH

    override fun askQuestion(chatId: Long): ChatResponse {
        return ChatResponse()
    }

    override fun answer(message: String, chatId: Long): ChatResponse {
        return ChatResponse()
    }
}
