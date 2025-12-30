package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.ChatResponse
import keo.whattodo.command.Choice
import keo.whattodo.service.UserRecommendService
import org.springframework.stereotype.Component

@Component
class ResultChatExchange(private val userRecommendService: UserRecommendService) : ChatExchange {
    override val order: ChatOrder = ChatOrder.RESULT

    override fun askQuestion(chatId: Long): ChatResponse {
        val recommends = userRecommendService.createRecommends(chatId)

        val response = RECOMMEND_TITLE + recommends
            .sortedBy { it.sequence }
            .joinToString("\n") { recommend ->
                RECOMMEND_FORMAT.format(recommend.sequence, recommend.title, recommend.reason)
            }
        val choices = recommends.map { Choice("${it.sequence}번", it.sequence.toString()) }
        return ChatResponse(response, choices)
    }

    override fun answer(message: String, chatId: Long): ChatResponse {
        val recommendId = message.toLong()
        val recommend = userRecommendService.selectRecommend(recommendId)

        val response = RECOMMEND_RESPONSE.format(
            recommend.title, recommend.step1, recommend.step2, recommend.step3, recommend.tip
        )
        return ChatResponse(response)
    }

    companion object {

        private const val RECOMMEND_TITLE = "# 오늘의 할 일 추천\n"
        private const val RECOMMEND_FORMAT = "### %d번 - %s\n- %s"
        private val RECOMMEND_RESPONSE = """
            # 🎉 활동 추천이 완료되었습니다!
            오늘의 추천 활동은 **"%s"** 입니다.
            - 활동 방법
              1. %s
              2. %s
              3. %s
            - 추가 팁: %s
            즐거운 시간 보내세요! 😊
        """.trimIndent()
    }
}
