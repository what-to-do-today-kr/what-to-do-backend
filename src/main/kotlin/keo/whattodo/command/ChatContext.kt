package keo.whattodo.command

class ChatContext(private val chats: Map<ChatOrder, ChatExchange>) {

    fun getFirstChat(): ChatExchange {
        return getChatOrThrow(ChatOrder.getStart())
    }

    fun getCurrentChat(order: ChatOrder): ChatExchange {
        return getChatOrThrow(order)
    }

    fun getNextChat(current: ChatExchange): ChatExchange {
        return getChatOrThrow(current.order.next)
    }

    private fun getChatOrThrow(order: ChatOrder): ChatExchange {
        return requireNotNull(chats[order]) { "No ChatExchange found for order: $order" }
    }
}