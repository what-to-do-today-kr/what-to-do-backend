package keo.whattodo.command

data class Choice(val description: String, val response: String) {

    constructor(response: String) : this(response, response)
}
