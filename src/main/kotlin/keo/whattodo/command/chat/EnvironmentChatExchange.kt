package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.ChatResponse
import keo.whattodo.command.Choice
import org.springframework.stereotype.Component

@Component
class EnvironmentChatExchange : ChatExchange {
    override val order: ChatOrder = ChatOrder.THIRD

    override fun askQuestion(): ChatResponse {
        return ChatResponse(QUESTION, CHOICES)
    }

    override fun answer(message: String): ChatResponse {
        return ChatResponse("${message}에서 활동하고 싶으시군요, 다음 질문으로 넘어가 볼게요!")
    }

    companion object {
        private val QUESTION = """
            ### 🌳 세 번째 질문
            어떤 환경에서 활동하고 싶으신가요?
        """.trimIndent()

        private val CHOICES: List<Choice> = listOf(
            Choice("집"),
            Choice("학교/사무실"),
            Choice("카페"),
            Choice("야외"),
        )
    }
}
