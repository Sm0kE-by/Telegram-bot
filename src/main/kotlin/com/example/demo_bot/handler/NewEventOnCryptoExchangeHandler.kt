package com.example.demo_bot.handler

import com.example.demo_bot.model.CommandName
import com.example.demo_bot.model.GameNameAttributes
import com.example.demo_bot.model.HandlerGamesName
import com.example.demo_bot.model.HandlerName
import com.example.demo_bot.util.createDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class NewEventOnCryptoExchangeHandler(private val gameNameAttributes: GameNameAttributes) : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE
    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {
        TODO("Not yet implemented")
    }

}