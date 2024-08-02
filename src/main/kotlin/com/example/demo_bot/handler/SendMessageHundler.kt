package com.example.demo_bot.handler

import com.example.demo_bot.model.BotAttributes
import com.example.demo_bot.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import com.example.demo_bot.util.getFromHandlerName
import com.example.demo_bot.util.getHashTagUtilCreatePost
import com.example.demo_bot.util.sendMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SendMessageHandler(private val botAttributes: BotAttributes) : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.SEND_MESSAGE

    val callbackBack = HandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {
        val fromHandlerName = arguments[1]
        val myChatId = callbackQuery.message.chatId.toString()
        val chatId = getChatIdForSendMessage(fromHandlerName)

        absSender.execute(
            sendMessage(
                botAttributes,
                getHashTagUtilCreatePost(HandlerName.MESSAGE_SKETCH),
                chatId,
                message
            )
        )
        absSender.execute(
            createDialogMenu(
                myChatId,
                "Сообщение отправлено",
                listOf(
                    listOf("$callbackBack|back" to "Готово"),
                ),
                fromHandlerName = getFromHandlerName(fromHandlerName)
            )
        )
    }
    private fun getChatIdForSendMessage(arguments: String): String {

        return when (arguments) {
            HandlerName.CREATE_POST_MENU.text -> "-1002115452577"
            HandlerName.DAILY_TASKS_IN_GAMES.text -> "-1002115452577"
            else -> "-1002115452577"
        }

    }
}
