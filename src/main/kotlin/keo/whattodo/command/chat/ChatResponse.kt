package keo.whattodo.command.chat

import keo.whattodo.command.Choice

data class ChatResponse(val message: String = "", val choices: List<Choice> = emptyList()) {

    fun isEmpty(): Boolean {
        return message.isBlank() && choices.isEmpty()
    }
}
