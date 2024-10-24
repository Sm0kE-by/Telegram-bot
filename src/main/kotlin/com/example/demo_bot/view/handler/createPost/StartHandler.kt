package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.MessageUser
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component

import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class StartHandler : CreatePostCallbackHandler {

    val callbackCreatePost = CreatePostHandlerName.CREATE_POST_MENU.text
    val callbackChangeAttributes = ChangeDataHandlerName.CHANGE_DATA_START_MENU.text

    override val name: CreatePostHandlerName = CreatePostHandlerName.START_HANDLER

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Добро пожаловать в нашего бота! Выбирите одны из функций!",
                listOf(
                    listOf("$callbackCreatePost|create_post" to "Создать пост"),
                    listOf("$callbackChangeAttributes|change_attributes" to "Изменить атрибуты"),
                ),
            )
        )
    }
}