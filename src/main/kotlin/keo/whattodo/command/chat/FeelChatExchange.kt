package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.Choice
import org.springframework.stereotype.Component

@Component
class FeelChatExchange : ChatExchange {

    override val order: ChatOrder = ChatOrder.THIRD
    override fun doBeforeInput(): ChatResponse {
        return ChatResponse(QUESTION, CHOICES)
    }

    override fun doAfterInput(message: String): ChatResponse {
        return ChatResponse()
    }


    companion object {
        private val QUESTION = """
            세 번째 질문입니다 😊
            오늘 기분이 어떠신가요?
        """.trimIndent()

        private val CHOICES: List<Choice> = listOf(
            Choice("심심함/지루함", "심심함/지루함"),
            Choice("불안함/초조함", "불안함/초조함"),
            Choice("편안함/차분함", "편안함/차분함"),
            Choice("행복함/기쁨", "행복함/기쁨"),
            Choice("피곤함/무기력", "피곤함/무기력"),
        )
    }
}
