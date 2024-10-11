package com.example.demo_bot.view.handler.changeData.exchange.proba

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.util.getSampleDataText
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class ChangeDataMessageCallbackHandler: ChangeData2CallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CHANGE_DATA_MESSAGE

    val callbackNext = ChangeDataHandlerName.SKETCH_DATA.text
    val callbackBack = ChangeDataHandlerName.CHANGE_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {
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
                    listOf(callbackNext to "Далее"),
                    listOf(callbackBack to "Назад"),
                ),
            )
        )
    }
}