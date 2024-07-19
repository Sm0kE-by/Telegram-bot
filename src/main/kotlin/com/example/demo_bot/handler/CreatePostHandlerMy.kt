package com.example.demo_bot.handler

import com.example.demo_bot.learn_bot.createMessage
import com.example.demo_bot.learn_bot.getInlineKeyboard
import com.example.demo_bot.model.BotAttributes
import com.example.demo_bot.model.HandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreatePostHandlerMy(private val botAttributes: BotAttributes) : MyCallbackHandlerBot {


    override val name: HandlerName = HandlerName.CREATE_POST_BY_CRYPTO


    override fun myProcessCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: List<String>, message: String) {

        val chatId = callbackQuery.message.chatId.toString()
        //val messageUser = callbackQuery.message.text.toString()

        absSender.execute(
            EditMessageReplyMarkup(
                chatId,
                callbackQuery.message.messageId,
                callbackQuery.inlineMessageId,
                getInlineKeyboard(emptyList())
            )
        )

        if (arguments.first() == "back") {
            absSender.execute(createMessage(chatId, "Абсолютно верно!"))
        } else {
            absSender.execute(
                createMessage(
                    chatId,
                    """
                    Шапка сообщения - 1234
                    Текст сообщения:
                      $message                  
                    
                   [YouTube]${botAttributes.attributes.youtube}
                   [TikTok]${botAttributes.attributes.tiktok}
                   [Instagram]${botAttributes.attributes.instagram}
                   [Telegraf]${botAttributes.attributes.telegraf}                                      
                """.trimIndent()
                )
            )
        }


    }
}