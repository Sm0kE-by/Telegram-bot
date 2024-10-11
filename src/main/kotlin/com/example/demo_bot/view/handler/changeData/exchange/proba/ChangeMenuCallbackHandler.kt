package com.example.demo_bot.view.handler.changeData.exchange.proba

import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class ChangeMenuCallbackHandler (private val exchangeLinkService: ExchangeLinkService) : ChangeData2CallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CHANGE_MENU

    val callbackNext = ChangeDataHandlerName.CHANGE_DATA_MESSAGE.text
    val callbackBack = ChangeDataHandlerName.CRUD_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {

        val listExchange = exchangeLinkService.getAll()

        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Выберите криптобиржу, которую необходимо изменить",
                getExchangeName(listExchange),
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