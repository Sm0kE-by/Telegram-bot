package com.example.demo_bot.view.handler.changeData.exchange

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CRUDExchangeCallbackHandler : ChangeDataCallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CRUD_MENU
    val text = "Что необходимо сделать в данном разделе?"

    val callbackCreateData = ChangeDataHandlerName.CREATE_DATA_EXCHANGE.text
    val callbackUpdateData = ChangeDataHandlerName.UPDATE_DATA_EXCHANGE.text
    val callbackDeleteData = ChangeDataHandlerName.DELETE_DATA_EXCHANGE.text
    val callbackBack = ChangeDataHandlerName.CHANGE_DATA_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                text,
                listOf(
                    listOf("$callbackCreateData|${name.text}" to "Создать"),
                    listOf("$callbackUpdateData|${name.text}" to "Изменить"),
                    listOf("$callbackDeleteData|${name.text}" to "Удалить"),
                    listOf(callbackBack to "Назад"),
                ),
            )
        )
    }
}