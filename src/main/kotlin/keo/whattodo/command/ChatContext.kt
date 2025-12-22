package keo.whattodo.command

class ChatContext(private val chats: Map<ChatOrder, ChatExchange>) {

    fun getFirstChat(): ChatExchange {
        return chats.get(ChatOrder.getStart())
            ?: throw IllegalStateException("No chat exchange found for order: ${ChatOrder.getStart()}")
    }

    fun getCurrentChat(order: ChatOrder): ChatExchange {
        return chats.get(order) ?: throw IllegalStateException("No chat exchange found for order: $order")
    }

    fun getNextChat(current: ChatExchange): ChatExchange {
        val nextOrder = current.order.next
        return chats.get(nextOrder) ?: throw IllegalStateException("No chat exchange found for order: $nextOrder")
    }
}