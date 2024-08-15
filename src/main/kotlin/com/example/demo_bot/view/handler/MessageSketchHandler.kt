package com.example.demo_bot.view.handler

import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.learn_bot.getInlineKeyboard
import com.example.demo_bot.view.model.BotAttributes
import com.example.demo_bot.view.model.enums.HandlerName
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
            list = getHashTagUtilCreatePost(fromHandlerName)
            attributesLink = botAttributes.attributesLink

            absSender.execute(
                createDialogMenu(
                    chatId = chatId,
                    text = getTextMessage(
                        message,
                        link
                    ),
                    inlineButtons = listOf(
                        listOf("$callbackSend|send" to "Отправить пост"),
                        listOf("$callbackBack|back" to "Назад"),
                    ),
                    fromHandlerName = getFromHandlerName(fromHandlerName)
                )
            )
        }
    }

    companion object {
        const val createNewPost = "create_new_post"
        const val inviteNewGame = "invite_new_game"
        const val newEventOnCryptoExchange = "new_event_on_crypto"
        const val dailyTaskInGames = "daily_task_in_games"
    }

    private fun getTextMessage(message: String, link: String) =
        if (link == "") {
            previewMessage(botAttributes, list, message)
        } else {
            previewMessageAndLinks(botAttributes, list, message, link)
        }


}