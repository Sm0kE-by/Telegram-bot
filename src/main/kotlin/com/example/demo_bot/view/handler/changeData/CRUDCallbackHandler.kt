package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.enums.ChangeDateHandlerName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CRUDCallbackHandler: ChangeDataCallbackHandler {

    override val name: ChangeDateHandlerName = ChangeDateHandlerName.CRUD_MENU

    val callbackCreateData = ChangeDateHandlerName.CREATE_DATA.text
    val callbackUpdateData = ChangeDateHandlerName.UPDATE_DATA.text
    val callbackDeleteData = ChangeDateHandlerName.DELETE_DATA.text
    val callbackBack = ChangeDateHandlerName.CHANGE_DATA_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, fromHandler: String) {

        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Что необходимо сделать в данном разделе?",
                listOf(
                    listOf("$callbackCreateData|$fromHandler" to "Создать"),
                    listOf("$callbackUpdateData|$fromHandler" to "Изменить"),
                    listOf("$callbackDeleteData|$fromHandler" to "Удалить"),
                    listOf(callbackBack to "Назад"),
                ),
            )
        )
    }
}