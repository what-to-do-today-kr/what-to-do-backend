package keo.whattodo.command

enum class ChatOrder {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    RESULT,
    FINISH,
    ;

    val next: ChatOrder
        get() = when (this) {
            FIRST -> SECOND
            SECOND -> THIRD
            THIRD -> FOURTH
            FOURTH -> RESULT
            RESULT -> FINISH
            FINISH -> FINISH
        }

    companion object {
        fun getStart(): ChatOrder = FIRST

        fun fromString(value: String): ChatOrder {
            return entries.find { it.name == value.uppercase() }
                ?: throw IllegalArgumentException("Invalid ChatOrder value: $value")
        }
    }
}

