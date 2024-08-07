package com.example.demo_bot.handler

import com.example.demo_bot.model.enums.HandlerName
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

interface MyCallbackHandlerBot {

    val name: HandlerName

    fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String,
        link: String = ""
    )
}