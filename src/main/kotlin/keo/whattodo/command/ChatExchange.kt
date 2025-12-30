package keo.whattodo.command

interface ChatExchange {
    val order: ChatOrder
    fun askQuestion(chatId: Long): ChatResponse
    fun answer(message: String, chatId: Long): ChatResponse
}
