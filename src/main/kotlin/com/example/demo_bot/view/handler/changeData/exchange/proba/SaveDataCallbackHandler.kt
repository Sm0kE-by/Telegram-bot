package com.example.demo_bot.view.handler.changeData.exchange.proba

import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SaveDataCallbackHandler(private val exchangeLinkService: ExchangeLinkService) : ChangeData2CallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.SAVE_DATA

    val callbackDone = CreatePostHandlerName.START_HANDLER.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {
        saveUpdate(changeDataModel)
        absSender.execute(
            createTextDialogMenu(
                chatId = chatId,
                text = "Операция выполнена успешно.",
                inlineButtons = listOf(
                    listOf(callbackDone to "Готово"),
                ),
            )
        )
    }

    private fun saveUpdate(changeDataModel: ChangeDataModel) {
        if (changeDataModel.exchange.name != null) {
            exchangeLinkService.create(changeDataModel.exchange)
        }
    }
}