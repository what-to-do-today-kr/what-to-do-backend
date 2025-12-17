package keo.whattodo.command

interface MessageSender {
    fun send(message: String)
    fun send(message: String, choices: List<Choice>)
}