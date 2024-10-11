package com.example.demo_bot.view.handler.changeData.exchange

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.util.getSampleDataText
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class UpdateExchangeCallbackHandler : ChangeDataCallbackHandler{

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.UPDATE_EXCHANGE

    val callbackNext = ChangeDataHandlerName.UPDATE_EXCHANGE_SKETCH.text
    val callbackBack = ChangeDataHandlerName.UPDATE_EXCHANGE_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, argument: String) {
        val text = getSampleDataText(
            "название биржи",
            "реферальную ссылку на аккаунт",
            "реферальный код"
        )
        absSender.execute(
            createTextDialogMenu(
                chatId = chatId,
                text = text,
                inlineButtons = listOf(
                    listOf("$callbackNext|${name.text}" to "Далее"),
                    listOf(callbackBack to "Назад"),
                ),
            )
        )
    }
}