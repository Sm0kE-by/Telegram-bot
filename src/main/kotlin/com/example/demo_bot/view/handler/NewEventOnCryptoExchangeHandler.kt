package com.example.demo_bot.view.handler

import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.createTextDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class NewEventOnCryptoExchangeHandler(
    private val exchangeLinkService: ExchangeLinkService,
) : MyCallbackHandlerBot {
    override val name: HandlerName = HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE
    val listExchange = exchangeLinkService.getAll()

    val callbackNext = HandlerName.CREATE_MESSAGE.text
    val callbackBack = HandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto,
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Выберете криптобиржу",
                getExchangeName(listExchange),
//                listOf(
//                    getExchangeName(listExchange),
//                    listOf("$callbackNext|ByBit" to "ByBit", "$callbackNext|OKX" to "OKX"),
//                    listOf("$callbackNext|Mexc" to "Mexc", "$callbackNext|BingX" to "BingX"),
//                    listOf("$callbackBack|back" to "Назад"),
//                ),
                //fromHandlerName = name
            )
        )
    }

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


