package com.example.demo_bot.learn_bot

import com.example.demo_bot.handler.MyCallbackHandlerBot
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class DevmarcBot(
    commands: Set<BotCommand>,
    callbackHandlers: Set<MyCallbackHandlerBot>,
    @Value("\${telegram.token}")
    token: String,
) : TelegramLongPollingCommandBot(token) {

    @Value("\${telegram.botName}")
    private val botName: String = ""
    private lateinit var handlerMapping: Map<String, MyCallbackHandlerBot>
    private lateinit var message: String

    init {
        registerAll(*commands.toTypedArray())
        handlerMapping = callbackHandlers.associateBy { it.name.text }
    }

    override fun getBotUsername(): String = botName

    override fun processNonCommandUpdate(update: Update) {
        if (update.hasMessage()) {
            val chatId = update.message.chatId.toString()
            if (update.message.hasText()) {
                message = update.message.text
                execute(createMessage(chatId, "Вы написали: *${update.message.text}*"))
            } else {
                execute(createMessage(chatId, "Я понимаю только текст!"))
            }
        }
        /**
         * Здесь мы добавили новую проверку hasCallbackQuery(), внутри которой извлекаем контекст callbackData.
         * Технически он представляет собой строку и вы можете поместить туда всё что угодно.
         * Это именно тот самый контекст, которого нам не хватает в функционале обычных кнопок.
         *
         * Перед началом обработки запроса нам нужно отправить клиентскому приложению AnswerCallbackQuery.
         * Мы как бы говорим, что запрос принят и мы начали его обработку. Если этого не сделать,
         * то клиент будет видеть анимацию обработки запроса и подумает, что наше приложение «зависло».
         *
         * Далее начинаем извлекать информацию из callbackData. Формат может быть любым, но при этом должен быть
         * единым для всех ваших callback-ов. Я здесь разделяю параметры вертикальной чертой,
         * причём первым параметром всегда ставлю текстовое имя из HandlerName.
         * По этому имени я извлекаю нужный обработчик из handlerMapping и передаю все остальные аргументы кроме
         * первого внутрь этого обработчика.
         */
        else if (update.hasCallbackQuery()) {
            val callbackQuery = update.callbackQuery
            val callbackData = callbackQuery.data

            val callbackQueryId = callbackQuery.id
            execute(AnswerCallbackQuery(callbackQueryId))

            val callbackArguments = callbackData.split("|")
            val callbackHandlerName = callbackArguments.first()

            // message = update.message.text

            handlerMapping.getValue(callbackHandlerName)
                .myProcessCallbackData(
                    absSender = this,
                    callbackQuery = callbackQuery,
                    arguments = callbackArguments.subList(1, callbackArguments.size),
                    message = message
                )
        }
    }


}