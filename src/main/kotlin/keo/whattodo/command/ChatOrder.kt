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
    }
}