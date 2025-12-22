package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.Choice
import org.springframework.stereotype.Component

@Component
class TimeChatExchange : ChatExchange {
    override val order: ChatOrder = ChatOrder.SECOND

    override fun askQuestion(): ChatResponse {
        return ChatResponse(QUESTION, CHOICES)
    }

    override fun answer(message: String): ChatResponse {
        return ChatResponse("${message} 투자 가능하시군요, 다음 질문으로 넘어가 볼게요!")
    }

    companion object {
        private val QUESTION = """
            ### ⏰ 두 번째 질문
            활동에 투자할 수 있는 시간을 선택해주세요.
        """.trimIndent()

        private val CHOICES: List<Choice> = listOf(
            Choice("30분"),
            Choice("1시간"),
            Choice("2시간"),
            Choice("3시간"),
        )
    }
}
