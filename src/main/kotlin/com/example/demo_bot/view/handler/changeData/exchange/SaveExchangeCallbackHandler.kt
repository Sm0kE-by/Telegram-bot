package com.example.demo_bot.view.handler.changeData.exchange

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SaveExchangeCallbackHandler : ChangeDataCallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CHANGE_DATA_SAVE

    val callbackDone = CreatePostHandlerName.START_HANDLER.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, argument: String) {
        absSender.execute(
            createTextDialogMenu(
                chatId = chatId,
                text = "Операция выполнена успешно.",
                inlineButtons = listOf(
                    listOf("$callbackDone|${name.text}" to "Готово"),
                ),
            )
        )
    }

}