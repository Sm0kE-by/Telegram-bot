package com.example.demo_bot.view.handler

import com.example.demo_bot.service.interfaces.Impl.AttributesServiceImpl
import com.example.demo_bot.view.model.BotAttributes
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SendMessageHandler(
    private val botAttributes: BotAttributes,
    private val attributesServiceImpl: AttributesServiceImpl,
    serviceImpl: AttributesServiceImpl
) : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.SEND_MESSAGE

    val callbackBack = HandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String,
        link: String
    ) {
        val fromHandlerName = arguments[1]
        val myChatId = callbackQuery.message.chatId.toString()
        val chatId = getChatIdForSendMessage(fromHandlerName)

        //Deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!
        val attr = attributesServiceImpl.getById(1)
        val listAttributes: List<String> = listOf(attr.attribute1, attr.attribute2, attr.attribute3, attr.attribute4, attr.attribute5)

        absSender.execute(
            sendMessage(
                botAttributes,
                //getHashTagUtilCreatePost(fromHandlerName),
                listAttributes,
                chatId,
                message,
                link
            )
        )
        absSender.execute(
            createDialogMenu(
                myChatId,
                "Сообщение отправлено",
                listOf(
                    listOf("$callbackBack|back" to "Готово"),
                ),
                fromHandlerName = getFromHandlerName(fromHandlerName)
            )
        )
    }
    private fun getChatIdForSendMessage(arguments: String): String {

        return when (arguments) {
            HandlerName.CREATE_POST_MENU.text -> "-1002115452577"
            HandlerName.DAILY_TASKS_IN_GAMES.text -> "-1002115452577"
            else -> "-1002115452577"
        }

    }
}
