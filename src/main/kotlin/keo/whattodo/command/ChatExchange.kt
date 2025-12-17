package keo.whattodo.command

import keo.whattodo.command.chat.ChatResponse

interface ChatExchange {
    val order: ChatOrder

    fun doBeforeInput() : ChatResponse
    fun doAfterInput(message: String) : ChatResponse
}
