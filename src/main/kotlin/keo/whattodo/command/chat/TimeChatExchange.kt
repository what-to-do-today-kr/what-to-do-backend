package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.Choice
import org.springframework.stereotype.Component

@Component
class TimeChatExchange : ChatExchange {
    override val order: ChatOrder = ChatOrder.SECOND

    override fun doBeforeInput(): ChatResponse {
        return ChatResponse(QUESTION, CHOICES)
    }

    override fun doAfterInput(message: String): ChatResponse {
        return ChatResponse()
    }

    companion object {
        private val QUESTION = """
            두 번째 질문입니다 ⏰
            활동에 투자할 수 있는 시간을 선택해주세요.
        """.trimIndent()

        private val CHOICES: List<Choice> = listOf(
            Choice("30분", "30분"),
            Choice("1시간", "1시간"),
            Choice("2시간", "2시간"),
            Choice("3시간", "3시간"),
        )
    }
}
