package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.util.getRowsOfButton
import com.example.demo_bot.view.model.MessageUser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class NewEventOnCryptoExchangeHandler(
    private val exchangeLinkService: ExchangeLinkService,
    private val messageService: MessageService,
) : CreatePostCallbackHandler {
    override val name: CreatePostHandlerName = CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE

    val callbackNext = CreatePostHandlerName.CREATE_MESSAGE.text
    val callbackBack = CreatePostHandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser,
    ) {
        val listExchange = exchangeLinkService.getAll().map { it.name }

        absSender.execute(
            createTextDialogMenu(
                chatId,
                messageService.getMessage("createPost.askNewEventOnCryptoExchangeHandler"),
                getRowsOfButton(listExchange, callbackNext, callbackBack)
            )
        )
    }
}


