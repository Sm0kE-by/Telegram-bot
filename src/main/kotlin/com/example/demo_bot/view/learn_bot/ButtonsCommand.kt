package com.example.demo_bot.view.learn_bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Давайте сделаем так, чтобы пользователю отображалось 4 кнопки, по 2 в ряд.
 * Для этого создадим ещё один обработчик ButtonsCommand для команды /buttons:
 */
@Component
class ButtonsCommand : BotCommand(CommandName2.BUTTONS.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.execute(
            createMessageWithSimpleButtons(
                chat.id.toString(),
                "Нажмите на одну из кнопок.",
                listOf(
                    listOf("Кнопка 1", "Кнопка 2"),
                )
            )
        )
    }
}