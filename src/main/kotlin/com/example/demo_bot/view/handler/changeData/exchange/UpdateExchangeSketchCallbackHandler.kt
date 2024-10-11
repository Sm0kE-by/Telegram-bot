package com.example.demo_bot.view.handler.changeData.exchange

import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class UpdateExchangeSketchCallbackHandler(private val exchangeLinkService: ExchangeLinkService) :
    ChangeDataCallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.UPDATE_EXCHANGE_SKETCH

    val callbackNext = ChangeDataHandlerName.UPDATE_EXCHANGE_SAVE.text
    val callbackBack = ChangeDataHandlerName.UPDATE_EXCHANGE.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, argument: String) {

        if (argument.isEmpty()) {
            absSender.execute(createMessage(chatId, "Вы не ввели сообщение!!!"))
        } else {
            val listNewArguments = argument.split("||")
            val oldArguments = exchangeLinkService.getById(listNewArguments[0].toInt())
            //TODO("Добавить реферальный код")
            val text =
                """
                *Старые данные:*
                *Название биржи* => ${oldArguments.name}
                *Реферальную ссылку на аккаунт* => ${oldArguments.link}
                *Реферальный код* => ${oldArguments.name}
                        
                *Новые данные:*
                *Название биржи* => ${listNewArguments[1]}
                *Реферальную ссылку на аккаунт* => ${listNewArguments[2]}
                *Реферальный код* => ${listNewArguments[3]}""".trimIndent()
            if (listNewArguments.size == 4) {
                absSender.execute(
                    createTextDialogMenu(
                        chatId = chatId,
                        text = text,
                        inlineButtons = listOf(
                            listOf("$callbackNext|${name.text}" to "Сохранить изменения"),
                            listOf(callbackBack to "Назад"),
                        ),
                    )
                )
            } else {
                absSender.execute(
                    createMessage(
                        chatId, "Сообщение введено неверно!!! \n*Пример:*\n" +
                                "название биржи||реферальную ссылку на аккаунт||реферальный код"
                    )
                )
            }
        }
    }
}