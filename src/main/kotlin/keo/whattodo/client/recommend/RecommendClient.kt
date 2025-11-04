package keo.whattodo.client.recommend

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.converter.BeanOutputConverter
import org.springframework.ai.google.genai.GoogleGenAiChatModel
import org.springframework.stereotype.Component

@Component
class RecommendClient(private val chatModel: GoogleGenAiChatModel) {

    private val chatClient: ChatClient = ChatClient.create(chatModel)

    fun recommendToDo(request: RecommendRequest): String {
        val systemPrompt = SYSTEM_PROMPT
            .replace("{jsonFormat}", RESPONSE_JSON_FORMAT)
        val userPrompt = RECOMMEND_PROMPT
            .replace("{energy}", request.energy.toString())
            .replace("{weather}", request.weatherScript)
            .replace("{feeling}", request.feelingScript)
            .replace("{goal}", request.goalScript)

        return chatClient.prompt()
            .system(systemPrompt)
            .user(userPrompt)
            .call()
            .content()
            ?.replace(RESPONSE_IGNORE_REGEX, "")
            ?.trim() ?: ""
    }

    companion object {

        private const val SYSTEM_PROMPT = """
            [역할]
            너는 15년차 심리상담가로서 일반적인 사람들에게 간단한 조언을 해주어야 해.
            
            [응답 조건]
            응답은 반드시 다음 JSON 형식에 맞춰 제공해 줘.
            
            {jsonFormat}
            
            - 응답에는 JSON만 포함하고, 다른 텍스트나 설명은 포함하지마.
        """

        private const val RECOMMEND_PROMPT = """
            나는 방금 지금 '오늘 뭐하지?'라는 생각이 들었어. 내 현재 상황과 심리 상태에 맞춰 실질적으로 바로 실행 가능한 활동을 3가지 내외 추천해 줘
            이 활동들은 단순히 시간을 보내는 것(Stimulus Avoidance)이 아니라, 제가 심리적으로 가장 필요로 하는 종류의 만족감을 채워줄 수 있는 활동이었으면 좋겠어
            이 활동을 보자마자 부담없이 바로 실행해 볼 수 있는 구체적인 행동들이었으면 좋겠어.
            
            - 현재 에너지 및 피로 수준 (1이 매우 지침, 5가 매우 활발) : {energy}
            - 오늘의 날씨 : {weather}
            - 현재 기분 및 정서 상태 : {feeling}
            - 활동 목표 및 원하는 만족감 : {goal}
        """

        private val RESPONSE_JSON_FORMAT: String = BeanOutputConverter(RecommendResponse::class.java).jsonSchema
        private val RESPONSE_IGNORE_REGEX = Regex("```[a-zA-Z]*\\s*|```")
    }
}
