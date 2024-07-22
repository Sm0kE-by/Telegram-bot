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
class CreateMessageHandler : MyCallbackHandlerBot{

    override val name: HandlerName = HandlerName.CREATE_MESSAGE

    val callbackNext = HandlerName.CREATE_NEW_POST_BY_CRYPTO.text
    val callbackBack = CommandName.START.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {

        val chatId = callbackQuery.message.chatId.toString()

        absSender.execute(

            createDialogMenu(
                chatId,
                "Введите текс сообщения",
                listOf(
                    listOf("$callbackNext|next" to "Далее"),
                    listOf("$callbackBack|back" to "Назад"),
                )
            )
        )
    }


}