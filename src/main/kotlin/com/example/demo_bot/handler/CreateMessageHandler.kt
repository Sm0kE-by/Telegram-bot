package com.example.demo_bot.handler

import com.example.demo_bot.learn_bot.getInlineKeyboard
import com.example.demo_bot.model.enums.CommandName
import com.example.demo_bot.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import com.example.demo_bot.util.getFromHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class CreateMessageHandler : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.CREATE_MESSAGE

    val callbackBack = HandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {

        val chatId = callbackQuery.message.chatId.toString()
        val fromHandlerName = arguments[1]

        absSender.execute(
            createDialogMenu(
                chatId,
                "Введите текс сообщения",
                listOf(
                    listOf("${getCallbackNext(fromHandlerName)}|next" to "Далее"),
                    listOf("$callbackBack|back" to "Назад"),
                ),
                fromHandlerName = name
            )
        )
    }

    //зачем?
    private fun getCallbackNext(handlerName: String) : String =
        when (handlerName) {
            HandlerName.CREATE_POST_MENU.text ->  HandlerName.MESSAGE_SKETCH.text
            HandlerName.DAILY_TASKS_IN_GAMES.text -> HandlerName.MESSAGE_SKETCH.text
            else -> CommandName.START.text
        }

}