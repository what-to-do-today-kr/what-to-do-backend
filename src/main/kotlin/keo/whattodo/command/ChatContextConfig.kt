package keo.whattodo.command

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatContextConfig {

    @Bean
    fun chatContext(chatExchanges: List<ChatExchange>): ChatContext {
        val chats = chatExchanges.associateBy { it.order }
        return ChatContext(chats)
    }
}