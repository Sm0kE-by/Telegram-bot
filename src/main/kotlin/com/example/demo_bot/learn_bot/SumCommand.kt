package com.example.demo_bot.learn_bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Давайте создадим новую команду /sum, которая на вход будет получать целые числа и возвращать их сумму.
 * Количество параметров в данном случае может быть любым.
 * Все параметры, которые получит SumCommand, содержатся в arguments. Преобразуем их в целые числа с помощью map() и toInt().
 * Затем суммируем их с помощью sum(). Наконец, формируем ответ с помощью метода joinToString(),
 * в котором перечислим через + все полученные аргументы-числа и после = выведем итоговую сумму.
 */
@Component
class SumCommand : BotCommand(CommandName2.SUM.text, ""){

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val numbers = arguments.map { it.toInt() }
        val sum = numbers.sum()
        absSender.execute(
            createMessage(
                chat.id.toString(),
                numbers.joinToString(separator = " + ", postfix = " = $sum"),
            )
        )
    }
}