package keo.whattodo.command

interface ChatExchange {
    val order: ChatOrder

    fun doBeforeInput(sender: MessageSender)
    fun doAfterInput(sender: MessageSender, message: String)
}