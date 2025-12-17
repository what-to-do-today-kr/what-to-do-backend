package keo.whattodo.command.chat

import keo.whattodo.command.ChatExchange
import keo.whattodo.command.ChatOrder
import keo.whattodo.command.MessageSender

class ResultChatExchange : ChatExchange {

    override val order: ChatOrder = ChatOrder.RESULT

    override fun doBeforeInput(sender: MessageSender) {
        sender.send("모든 질문이 완료되었습니다! 결과를 확인해보세요.")
    }

    override fun doAfterInput(sender: MessageSender, message: String) {
    }
}