package keo.whattodo.command

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatContextConfig {

    @Bean
    fun chatContext(chatExchanges: List<ChatExchange>): ChatContext {
        require(chatExchanges.isNotDuplicateOrders())
        require(chatExchanges.isExistAllOrders())

        val chats = chatExchanges.associateBy { it.order }
        return ChatContext(chats)
    }

    private fun List<ChatExchange>.isNotDuplicateOrders(): Boolean {
        return this.size == this.map { it.order }.toSet().size
    }

    private fun List<ChatExchange>.isExistAllOrders(): Boolean {
        val allOrders = ChatOrder.entries.toSet()
        val existingOrders = this.map { it.order }.toSet()
        return allOrders == existingOrders
    }
}