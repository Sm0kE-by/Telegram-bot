package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.view.model.MessageUser
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import org.telegram.telegrambots.meta.bots.AbsSender

interface CreatePostCallbackHandler {

    val name: CreatePostHandlerName

    fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser,
    )
}