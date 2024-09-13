package com.example.demo_bot.view.handler

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.createTextDialogMenu
import org.springframework.stereotype.Component

import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class StartHandler : MyCallbackHandlerBot {

    val callbackCreatePost = HandlerName.CREATE_POST_MENU.text
    val callbackChangeAttributes = HandlerName.CHANGE_ATTRIBUTES.text

    override val name: HandlerName = HandlerName.START_HANDLER

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Добро пожаловать в нашего бота! Выбирите одны из функций!",
                listOf(
                    listOf("$callbackCreatePost|create_post" to "Создать пост"),
                    listOf("$callbackChangeAttributes|change_attributes" to "Изменить атрибуты"),
                ),
                //fromHandlerName = HandlerName.CREATE_POST_MENU
            )
        )
    }
}