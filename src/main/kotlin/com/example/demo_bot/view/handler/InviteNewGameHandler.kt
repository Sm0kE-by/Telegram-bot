package com.example.demo_bot.view.handler

import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import com.example.demo_bot.view.model.MessageModel
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
        message: MessageModel,
    ) {

        val chatId = callbackQuery.message.chatId.toString()

        absSender.execute(
            createDialogMenu(
                chatId,
                "Вы выбрали пункт \"Приглошение в новую игру\" для продолжения нажмите \"Далее\"",
                listOf(
                    listOf("$callbackNext|empty" to "Далее"),
                    listOf("$callbackBack|empty" to "Назад"),
                ),
                //fromHandlerName = name
            )
        )
    }
}