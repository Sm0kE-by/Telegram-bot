package com.example.demo_bot.view.learn_bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Здесь мы сначала отправляем EditMessageReplyMarkup с помощью absSender.
 * Он убирает кнопки с вариантами ответа из конкретного сообщения, т.к. если один из вариантов выбран,
 * то кнопки больше не нужны. Мы просто передаём в getInlineKeyboard() пустой список.
 * Сходным образом можно отредактировать исходное сообщение. Например, зафиксировать вариант ответа.
 *
 * Далее извлекаем из аргументов обратного вызова выбранный вариант ответа.
 * Помним, что первым аргументом изначально было имя обратного вызова, но мы его отсекаем на уровне DevmarkBot.
 * Проверяем вариант и отправляем новое сообщение в зависимости от правильности ответа.
 */
@Component
class QuizAnswerHandler : CallbackHandler {

    override val name: HandlerName2 = HandlerName2.QUIZ_ANSWER

    override fun processCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>
    ) {

        val chatId = callbackQuery.message.chatId.toString()

        absSender.execute(
            EditMessageReplyMarkup(
                chatId,
                callbackQuery.message.messageId,
                callbackQuery.inlineMessageId,
                getInlineKeyboard(emptyList())
            )
        )

        if (arguments.first() == "b") {
            absSender.execute(createMessage(chatId, "Абсолютно верно!"))
        } else {
            absSender.execute(createMessage(chatId, "К сожалению, Вы ошиблись..."))
        }
    }
}