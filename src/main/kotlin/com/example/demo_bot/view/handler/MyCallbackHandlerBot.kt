package com.example.demo_bot.view.handler

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.view.model.MessageModel
import com.example.demo_bot.view.model.enums.HandlerName
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

interface MyCallbackHandlerBot {

    val name: HandlerName

    fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto,
    )
}