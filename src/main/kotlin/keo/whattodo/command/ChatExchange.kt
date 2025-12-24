package keo.whattodo.command

interface ChatExchange {
    val order: ChatOrder
    fun askQuestion(): ChatResponse
    fun answer(message: String): ChatResponse
}
