package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.Choice
import org.springframework.stereotype.Component

@Component
class EnergyChatExchange : ChatExchange {
    override val order: ChatOrder = ChatOrder.FIRST
    override fun doBeforeInput(): ChatResponse {
        return ChatResponse(REQUEST, CHOICES)
    }

    override fun doAfterInput(message: String): ChatResponse {
        return ChatResponse()
    }


    companion object {
        private val REQUEST = """
            안녕하세요! 저는 당신의 여가 시간을 도와드릴 심리 기반 활동 큐레이터예요 🌟
            오늘 자유 시간에 뭘 해야 할지 고민되시나요?
            딱 5가지 질문에만 답해주시면, 꼭 맞는 활동을 추천해드릴게요!
            첫 번째 질문이에요 💪
            지금 활동에 투자할 수 있는 신체적/정신적 에너지는 어느 정도인가요?
        """.trimIndent()

        private val CHOICES: List<Choice> = listOf(
            Choice("1점 - 매우 지침", "1"),
            Choice("2점 - 조금 지침", "2"),
            Choice("3점 - 보통", "3"),
            Choice("4점 - 활발", "4"),
            Choice("5점 - 매우 활발", "5"),
        )
    }
}
