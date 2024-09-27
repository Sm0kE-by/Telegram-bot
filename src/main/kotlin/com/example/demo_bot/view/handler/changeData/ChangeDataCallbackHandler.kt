package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.view.model.enums.ChangeDateHandlerName
import org.telegram.telegrambots.meta.bots.AbsSender

interface ChangeDataCallbackHandler {


    val name: ChangeDateHandlerName

    fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        fromHandler: String,
    )
}

