package com.example.demo_bot.handler

import com.example.demo_bot.learn_bot.createMessage
import com.example.demo_bot.learn_bot.getInlineKeyboard
import com.example.demo_bot.model.BotAttributes
import com.example.demo_bot.model.HandlerName
import com.example.demo_bot.model.HashtagModel
import com.example.demo_bot.util.getHashTagUtilCreatePost
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreatePostHandlerMy(private val botAttributes: BotAttributes) : MyCallbackHandlerBot {


    override val name: HandlerName = HandlerName.CREATE_NEW_POST_BY_CRYPTO
    lateinit var list: List<String>


    override fun myProcessCallbackData(
        absSender: AbsSender, callbackQuery: CallbackQuery, arguments: List<String>, message: String
    ) {

        val chatId = callbackQuery.message.chatId.toString()
        //val messageUser = callbackQuery.message.text.toString()

        //???????????????
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
            list = getHashTagUtilCreatePost(HandlerName.CREATE_NEW_POST_BY_CRYPTO)

            absSender.execute(
                createMessage(
                    chatId,
                    """                    
                    [${botAttributes.attributes.headName}]${botAttributes.attributes.headLink}
                    
                    $message      
                                
                    $list
                  
                    [${botAttributes.attributes.youtubeAttributes}]${botAttributes.attributes.youtube} | [${botAttributes.attributes.tiktokAttributes}]${botAttributes.attributes.tiktok} | [${botAttributes.attributes.instagramAttributes}]${botAttributes.attributes.instagram} | [${botAttributes.attributes.telegraphAttributes}]${botAttributes.attributes.telegraph}                                      
                """.trimIndent()
                )
            )
        }


    }

}