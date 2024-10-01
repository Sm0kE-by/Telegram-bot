package com.example.demo_bot.view.handler.changeData.exchange

import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.telegram.telegrambots.meta.bots.AbsSender

class SketchExchangeCallbackHandler: ChangeDataCallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CHANGE_DATA_SKETCH

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String) {
        TODO("Not yet implemented")
    }
}