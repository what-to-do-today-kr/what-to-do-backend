package keo.whattodo.command

enum class ChatOrder {
    FIRST,
    SECOND,
    THIRD,
    RESULT,
    ;

    val next: ChatOrder
        get() = when (this) {
            FIRST -> SECOND
            SECOND -> THIRD
            THIRD -> RESULT
            RESULT -> RESULT
        }

    companion object {
        fun getStart(): ChatOrder = FIRST

        fun fromString(value: String): ChatOrder {
            return entries.find { it.name == value.uppercase() }
                ?: throw IllegalArgumentException("Invalid ChatOrder value: $value")
        }
    }
}
