package keo.whattodo.client.ai.google

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.google.genai.GoogleGenAiChatModel
import org.springframework.stereotype.Component

@Component
class RecommendClient(val chatModel: GoogleGenAiChatModel) {

    fun run(): String {
        val chatClient = ChatClient.create(chatModel)
        return chatClient.prompt()
            .user("나 처음 요청해보는데, 잘 되니?")
            .call()
            .content() ?: ""
    }
}
