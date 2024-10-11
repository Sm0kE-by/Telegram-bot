package com.example.demo_bot.view.handler.changeData.exchange

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreateExchangeSketchCallbackHandler : ChangeDataCallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CREATE_EXCHANGE_SKETCH

    val callbackNext = ChangeDataHandlerName.CREATE_EXCHANGE_SAVE.text
    val callbackBack = ChangeDataHandlerName.CREATE_EXCHANGE.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, argument: String) {

        if (argument.isEmpty()) {
            absSender.execute(createMessage(chatId, "Вы не ввели сообщение!!!"))
        } else {
            val listArguments = argument.split("||")
            if (listArguments.size == 3) {
                absSender.execute(
                    createTextDialogMenu(
                        chatId = chatId,
                        text = "*Название биржи* => ${listArguments[0]}" +
                                "\n*Реферальную ссылку на аккаунт* => ${listArguments[1]}" +
                                "\n*Реферальный код* => ${listArguments[2]}",
                        inlineButtons = listOf(
                            listOf("$callbackNext|${name.text}" to "Сохранить изменения"),
                            listOf(callbackBack to "Назад"),
                        ),
                    )
                )
            } else {
                absSender.execute(createMessage(chatId, "Сообщение введено неверно!!! \n*Пример:*\n" +
                        "название биржи||реферальную ссылку на аккаунт||реферальный код"))
            }
        }
    }
}