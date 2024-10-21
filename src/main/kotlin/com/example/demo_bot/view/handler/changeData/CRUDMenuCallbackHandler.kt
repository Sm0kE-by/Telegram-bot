package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CRUDMenuCallbackHandler : ChangeDataCallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CRUD_MENU
    val text = "Что необходимо сделать в данном разделе?"

    val callbackCreateData = ChangeDataHandlerName.CHANGE_DATA_MESSAGE.text
    val callbackUpdateData = ChangeDataHandlerName.CHANGE_MENU.text
    val callbackDeleteData = ChangeDataHandlerName.CHANGE_MENU.text
    val callbackBack = ChangeDataHandlerName.CHANGE_DATA_START_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                text,
                listOf(
                    listOf("$callbackCreateData|${ChangeDataHandlerName.CREATE_DATA.text}" to "Создать"),
                    listOf("$callbackUpdateData|${ChangeDataHandlerName.UPDATE_DATA.text}" to "Изменить"),
                    listOf("$callbackDeleteData|${ChangeDataHandlerName.DELETE_DATA.text}" to "Удалить"),
                    listOf(callbackBack to "Назад"),
                ),
            )
        )
    }

}