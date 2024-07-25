package com.example.demo_bot.handler

import com.example.demo_bot.learn_bot.createMessage
import com.example.demo_bot.model.BotAttributes
import com.example.demo_bot.model.HandlerName
import com.example.demo_bot.util.getHashTagUtilCreatePost
import com.example.demo_bot.util.sendMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SendMessageHandler(private val botAttributes: BotAttributes) : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.SEND_MESSAGE


    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {

        //val chatId = callbackQuery.message.chatId.toString()
        val chatId = "-1002115452577"

        absSender.execute(
            sendMessage(
                botAttributes,
                getHashTagUtilCreatePost(HandlerName.CREATE_NEW_POST_BY_CRYPTO),
                chatId,
                message
            )
        )
    }
}
