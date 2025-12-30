package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.ChatResponse
import keo.whattodo.command.Choice
import keo.whattodo.service.UserStateService
import org.springframework.stereotype.Component

@Component
class MoodChatExchange(private val userStateService: UserStateService) : ChatExchange {
    override val order: ChatOrder = ChatOrder.FOURTH

    override fun askQuestion(chatId: Long): ChatResponse {
        return ChatResponse(QUESTION, CHOICES)
    }

    override fun answer(message: String, chatId: Long): ChatResponse {
        userStateService.update(chatId) { this.mood = message }
        return ChatResponse("$message 기분이시군요.\n$RESPONSE")
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

        private val RESPONSE = """
            ### 🎉 모든 질문이 완료되었습니다!
            이제 당신에게 딱 맞는 활동을 추천해드릴게요. 잠시만 기다려주세요~
        """.trimIndent()
    }
}
