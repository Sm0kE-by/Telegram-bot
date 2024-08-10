package com.example.demo_bot.handler

import com.example.demo_bot.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreatePostAboutCrypto  : MyCallbackHandlerBot {
    override val name: HandlerName = HandlerName.CREATE_POST_ABOUT_CRYPTO

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
                "Вы выбрали пункт \"Создать пост о криптовалюте\" для продолжения нажмите \"Далее\"",
                listOf(
                    listOf("$callbackNext|next" to "Далее"),
                    listOf("$callbackBack|back" to "Назад"),
                ),
                fromHandlerName = name
            )
        )
    }
}