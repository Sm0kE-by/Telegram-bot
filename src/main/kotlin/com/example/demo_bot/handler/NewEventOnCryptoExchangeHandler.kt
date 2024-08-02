package com.example.demo_bot.handler

import com.example.demo_bot.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class NewEventOnCryptoExchangeHandler() : MyCallbackHandlerBot {
    override val name: HandlerName = HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE

    val callbackNext = HandlerName.CREATE_MESSAGE.text
    val callbackBack = HandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {

        val chatId = callbackQuery.message.chatId.toString()
        val fromHandlerName = arguments[1]

        absSender.execute(
            createDialogMenu(
                chatId,
                "Выберете криптобиржу",
                    listOf(
                        listOf("$callbackNext|ByBit" to "Далее", "$callbackNext|OKX" to "Далее"),
                        listOf("$callbackNext|Mexc" to "Далее", "$callbackNext|BingX" to "Далее"),
                        listOf("$callbackBack|back" to "Назад"),
                ),
                fromHandlerName = name
            )
        )
    }
}


