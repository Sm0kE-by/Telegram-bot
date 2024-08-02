package com.example.demo_bot.command

import com.example.demo_bot.model.enums.CommandName
import com.example.demo_bot.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class StartCommand : BotCommand(CommandName.START.text, "") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {

        val callbackCreatePost = HandlerName.CREATE_POST_MENU.text
        val callbackChangeAttributes = HandlerName.CHANGE_ATTRIBUTES.text

        absSender.execute(
            createDialogMenu(
                chat.id.toString(),
                "Добро пожаловать в нашего бота! Выбирите одны из функций!",
                listOf(
                    listOf("$callbackCreatePost|create_post" to "Создать пост"),
                    listOf("$callbackChangeAttributes|change_attributes" to "Изменить атрибуты"),
                ),
                fromHandlerName = HandlerName.CREATE_POST_MENU
            )
        )
    }
}