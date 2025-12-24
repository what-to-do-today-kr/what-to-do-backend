package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.ChatResponse
import keo.whattodo.command.Choice
import org.springframework.stereotype.Component

@Component
class FeelChatExchange : ChatExchange {
    override val order: ChatOrder = ChatOrder.FOURTH

    override fun askQuestion(): ChatResponse {
        return ChatResponse(QUESTION, CHOICES)
    }

    override fun answer(message: String): ChatResponse {
        return ChatResponse("${message} 기분이시군요, 이제 결과를 보여드릴께요 잠시만 기다려주세요~")
    }


    companion object {
        private val QUESTION = """
            ### 😊 마지막 질문
            오늘 기분이 어떠신가요?
        """.trimIndent()

        private val CHOICES: List<Choice> = listOf(
            Choice("심심함/지루함"),
            Choice("불안함/초조함"),
            Choice("편안함/차분함"),
            Choice("행복함/기쁨"),
            Choice("피곤함/무기력"),
        )
    }
}
