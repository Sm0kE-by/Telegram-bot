package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.MessageUser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class NewEventOnCryptoExchangeHandler(
    private val exchangeLinkService: ExchangeLinkService,
) : CreatePostCallbackHandler {
    override val name: CreatePostHandlerName = CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE
    val listExchange = exchangeLinkService.getAll()

    val callbackNext = CreatePostHandlerName.CREATE_MESSAGE.text
    val callbackBack = CreatePostHandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser,
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Выберите криптобиржу",
                getExchangeName(listExchange),
            )
        )
    }

    //TODO update fun
    private fun getExchangeName(listExchange: List<ExchangeLinkDto>): List<List<Pair<String, String>>> {
        val list = ArrayList<List<Pair<String, String>>>()
        for (i in listExchange.indices step 2) {
            if (i + 1 <= listExchange.size - 1) {
                list.add(
                    listOf(
                        "$callbackNext|${listExchange[i].name}" to listExchange[i].name,
                        "$callbackNext|${listExchange[i + 1].name}" to listExchange[i + 1].name
                    )
                )
            } else {
                list.add(
                    listOf(
                        "$callbackNext|${listExchange[i].name}" to listExchange[i].name
                    )
                )

            }
        }
        list.add(listOf("$callbackBack|back" to "Назад"))
        return list
    }
}


