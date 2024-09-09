package com.example.demo_bot.view.handler

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import com.example.demo_bot.view.model.MessageModel
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
        chatId: String,
        message: MessageUserDto
    ) {
        absSender.execute(
            createDialogMenu(
                chatId,
                "Вы выбрали пункт \"Создать пост о криптовалюте\" для продолжения нажмите \"Далее\"",
                listOf(
                    listOf("$callbackNext|empty" to "Далее"),
                    listOf("$callbackBack|empty" to "Назад"),
                ),
//                fromHandlerName = name
            )
        )
    }
}