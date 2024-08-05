package com.example.demo_bot.handler

import com.example.demo_bot.learn_bot.createMessage
import com.example.demo_bot.learn_bot.getInlineKeyboard
import com.example.demo_bot.model.BotAttributes
import com.example.demo_bot.model.enums.HandlerName
import com.example.demo_bot.util.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class MessageSketchHandler(private val botAttributes: BotAttributes) : MyCallbackHandlerBot {


    override val name: HandlerName = HandlerName.MESSAGE_SKETCH
    lateinit var list: List<String>
    lateinit var attributesLink: String

    val callbackSend = HandlerName.SEND_MESSAGE.text
    val callbackBack = HandlerName.CREATE_MESSAGE.text

    override fun myProcessCallbackData(
        absSender: AbsSender, callbackQuery: CallbackQuery, arguments: List<String>, message: String, link: String
    ) {
        val fromHandlerName = arguments[1]
        val chatId = callbackQuery.message.chatId.toString()
        //val messageUser = callbackQuery.message.text.toString()

//        //???????????????
//        absSender.execute(
//            EditMessageReplyMarkup(
//                chatId,
//                callbackQuery.message.messageId,
//                callbackQuery.inlineMessageId,
//                getInlineKeyboard(emptyList())
//            )
//        )

        if (message.isEmpty()) {
            absSender.execute(createMessage(chatId, "Вы не ввели сообщение!!!"))
//            absSender.execute(
//                createDialogMenu(
//                    chatId,
//                    "Введите текс сообщения",
//                    listOf(
//                        listOf("$callbackNext|next" to "Далее"),
//                        listOf("$callbackBack|back" to "Назад"),
//                    ),
//                    fromHandlerName = name
//                )
//            )
        } else {
            list = getHashTagUtilCreatePost(HandlerName.MESSAGE_SKETCH)
            attributesLink = botAttributes.attributesLink

            absSender.execute(
                createDialogMenu(
                    chatId = chatId,
                    text = previewMessageAndLinks(
                        botAttributes,
                        getHashTagUtilCreatePost(HandlerName.MESSAGE_SKETCH),
                        message,
                        link,
                    ),
                   inlineButtons =  listOf(
                       listOf("$callbackSend|send" to "Отправить пост"),
                        listOf("$callbackBack|back" to "Назад"),
                    ),
                    fromHandlerName = getFromHandlerName(fromHandlerName)
                )
            )
        }
    }
}