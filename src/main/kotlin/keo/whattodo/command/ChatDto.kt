package keo.whattodo.command

data class ChatResponse(
    val message: String = "",
    val choices: List<Choice> = emptyList(),
) {
    fun isEmpty(): Boolean {
        return message.isBlank() && choices.isEmpty()
    }
}

data class Choice(val description: String, val response: String) {
    constructor(response: String) : this(response, response)
}
