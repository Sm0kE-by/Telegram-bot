package com.example.demo_bot.command

import com.example.demo_bot.model.CommandName
import com.example.demo_bot.model.HandlerName
import com.example.demo_bot.util.createPost
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class PostCommand : BotCommand(CommandName.POST.text, "") {

    //
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {

        val callback = HandlerName.CREATE_POST_BY_CRYPTO.text

        absSender.execute(
            createPost(
                chat.id.toString(),
                "Введите текс поста",
                listOf(
                    listOf("$callback|done" to "Готово"),
                    listOf("$callback|back" to "Назад"),
                )
            )
        )
    }


}