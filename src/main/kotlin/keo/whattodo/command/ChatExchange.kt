package keo.whattodo.command

import keo.whattodo.command.chat.ChatResponse

interface ChatExchange {
    val order: ChatOrder
    fun askQuestion(): ChatResponse
    fun answer(message: String): ChatResponse
}
