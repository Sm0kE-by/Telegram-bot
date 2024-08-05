package com.example.demo_bot.handler

import com.example.demo_bot.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class InviteNewGameHandler : MyCallbackHandlerBot {
    override val name: HandlerName = HandlerName.INVITE_NEW_GAME

    val callbackNext = HandlerName.CREATE_MESSAGE.text
    val callbackBack = HandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String,
        link: String
    ) {

        val chatId = callbackQuery.message.chatId.toString()

        absSender.execute(
            createDialogMenu(
                chatId,
                "Введите описание игры и её ссылку",
                listOf(
                    listOf("$callbackNext|next" to "Далее"),
                    listOf("$callbackBack|back" to "Назад"),
                ),
                fromHandlerName = name
            )
        )
    }
}