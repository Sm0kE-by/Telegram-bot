package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreatePostAboutCrypto  : CreatePostCallbackHandler {
    override val name: CreatePostHandlerName = CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO

    val callbackNext = CreatePostHandlerName.CREATE_MESSAGE.text
    val callbackBack = CreatePostHandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto
    ) {
        absSender.execute(
            createTextDialogMenu(
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