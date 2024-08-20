package com.example.demo_bot.view.handler

import com.example.demo_bot.view.learn_bot.getInlineKeyboard
import com.example.demo_bot.view.model.enums.CommandName
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import com.example.demo_bot.util.getFromHandlerName
import com.example.demo_bot.view.model.MessageModel
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class CreateMessageHandler : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.CREATE_MESSAGE

    val callbackNext = HandlerName.MESSAGE_SKETCH.text
    val callbackBack = HandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        message: MessageModel,
    ) {

        val chatId = callbackQuery.message.chatId.toString()
//        val fromHandlerName = arguments[1]

        absSender.execute(
            createDialogMenu(
                chatId,
                "Введите текс сообщения",
                listOf(
                    listOf("$callbackNext|next" to "Далее"),
                    listOf("$callbackBack|back" to "Назад"),
                ),
  //              fromHandlerName = getFromHandlerName(fromHandlerName)
            )
        )
    }

    //зачем?
//    private fun getFromHandlerName(handlerName: HandlerName) : String =
//        when (handlerName) {
//            HandlerName.CREATE_POST_MENU.text ->  HandlerName.MESSAGE_SKETCH.text
//            HandlerName.DAILY_TASKS_IN_GAMES.text -> HandlerName.MESSAGE_SKETCH.text
//            else -> CommandName.START.text
//        }

}