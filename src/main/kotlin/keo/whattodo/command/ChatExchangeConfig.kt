package keo.whattodo.command

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatExchangeConfig {

    @Bean
    fun chatExchagesMap(chatExchanges: List<ChatExchange>): Map<ChatOrder, ChatExchange> {
        return chatExchanges.associateBy { it.order }
    }
}