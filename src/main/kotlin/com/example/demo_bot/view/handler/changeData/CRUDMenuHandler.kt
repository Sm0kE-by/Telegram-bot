package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CRUDMenuHandler(private val messageService: MessageService) : ChangeDataHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CRUD_MENU
    val text = messageService.getMessage("changeDate.askCRUDMenuHandler")

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
                    listOf("$callbackCreateData|${ChangeDataHandlerName.CREATE_DATA.text}" to messageService.getMessage("button.create")),
                    listOf("$callbackUpdateData|${ChangeDataHandlerName.UPDATE_DATA.text}" to messageService.getMessage("button.update")),
                    listOf("$callbackDeleteData|${ChangeDataHandlerName.DELETE_DATA.text}" to messageService.getMessage("button.delete")),
                    listOf(callbackBack to messageService.getMessage("button.back")),
                ),
            )
        )
    }
}