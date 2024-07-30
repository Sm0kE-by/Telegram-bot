package com.example.demo_bot.handler

import com.example.demo_bot.learn_bot.createMessage
import com.example.demo_bot.learn_bot.getInlineKeyboard
import com.example.demo_bot.model.BotAttributes
import com.example.demo_bot.model.HandlerName
import com.example.demo_bot.util.createDialogMenu
import com.example.demo_bot.util.getHashTagUtilCreatePost
import com.example.demo_bot.util.previewMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreatePostHandlerMy(private val botAttributes: BotAttributes) : MyCallbackHandlerBot {


    override val name: HandlerName = HandlerName.CREATE_NEW_POST_BY_CRYPTO
    lateinit var list: List<String>
    lateinit var attributesLink: String

    val callbackSend = HandlerName.SEND_MESSAGE.text
    val callbackNext = HandlerName.CREATE_NEW_POST_BY_CRYPTO.text
    val callbackBack = HandlerName.CREATE_MESSAGE.text

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

//        if (arguments.first() == "back") {
//            absSender.execute(createMessage(chatId, "Абсолютно верно!"))
//        } else
        if (message.isEmpty()) {
            absSender.execute(createMessage(chatId, """
                
                Вы не ввели сообщение!!!
                
                """.trimIndent() ))
            absSender.execute(
                createDialogMenu(
                    chatId,
                    "Введите текс сообщения",
                    listOf(
                        listOf("$callbackNext|next" to "Далее"),
                        listOf("$callbackBack|back" to "Назад"),
                    )
                )
            )
        } else {
            list = getHashTagUtilCreatePost(HandlerName.CREATE_NEW_POST_BY_CRYPTO)
            attributesLink = botAttributes.attributesLink

            absSender.execute(
                createDialogMenu(
                    chatId = chatId,
                    text = previewMessage(
                        botAttributes,
                        getHashTagUtilCreatePost(HandlerName.CREATE_NEW_POST_BY_CRYPTO),
                        message
                    ),
                   inlineButtons =  listOf(
                       listOf("$callbackSend|send" to "Отправить пост"),
                        listOf("$callbackBack|back" to "Назад"),
                    )
                )
            )
        }
    }
}