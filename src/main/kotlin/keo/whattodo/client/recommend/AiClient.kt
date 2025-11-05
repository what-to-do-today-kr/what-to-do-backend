package keo.whattodo.client.recommend

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.google.genai.GoogleGenAiChatModel
import org.springframework.stereotype.Component

@Component
class AiClient(private val chatModel: GoogleGenAiChatModel) {

    private val chatClient: ChatClient = ChatClient.create(chatModel)

    fun call(systemPrompt: String, userPrompt: String): String {
        return chatClient.prompt()
            .system(systemPrompt)
            .user(userPrompt)
            .call()
            .content() ?: ""
    }
}
