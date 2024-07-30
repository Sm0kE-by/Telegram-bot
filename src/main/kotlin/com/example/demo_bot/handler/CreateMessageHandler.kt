package com.example.demo_bot.handler

import com.example.demo_bot.model.CommandName
import com.example.demo_bot.model.HandlerName
import com.example.demo_bot.util.createDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class CreateMessageHandler : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.CREATE_MESSAGE

    lateinit var callbackNext: String

    //    = HandlerName.CREATE_NEW_POST_BY_CRYPTO.text
    val callbackBack = CommandName.START.text

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
                )
            )
        )
    }

    private fun getCallbackNext(handlerName: String) =

        when (handlerName) {
            HandlerName.CREATE_POST_MENU.text -> callbackNext = HandlerName.CREATE_NEW_POST_BY_CRYPTO.text
            HandlerName.DAILY_TASKS_IN_GAMES.text -> callbackNext = HandlerName.DAILY_TASKS_IN_GAMES.text
            else -> {}
        }
}