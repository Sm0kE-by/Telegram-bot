package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.telegram.telegrambots.meta.bots.AbsSender

interface ChangeDataCallbackHandler {

    val name: ChangeDataHandlerName

    fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        changeDataModel: ChangeDataModel
    )
}