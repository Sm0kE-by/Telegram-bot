package com.example.demo_bot.handler

import com.example.demo_bot.learn_bot.createMessage
import com.example.demo_bot.model.HandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SendMessageHandler : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.SEND_MESSAGE


    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {

        val chatId = callbackQuery.message.chatId.toString()

        absSender.execute(
            createMessage("-1002115452577", message)
        )
    }
}
