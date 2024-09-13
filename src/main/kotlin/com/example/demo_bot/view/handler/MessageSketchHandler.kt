package com.example.demo_bot.view.handler

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.model.BotAttributes
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class MessageSketchHandler(
    private val botAttributes: BotAttributes,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val sendMessageHandler: SendMessageHandler
) : MyCallbackHandlerBot {

    companion object {
        const val TEXT_SIZE = 4096
        const val TEXT_PHOTO_SIZE = 1024
    }

    override val name: HandlerName = HandlerName.MESSAGE_SKETCH

    //    lateinit var list: List<String>
    lateinit var attributesLink: String

    val callbackSend = HandlerName.SEND_MESSAGE.text
    val callbackBack = HandlerName.CREATE_MESSAGE.text
    private val socialLink = socialMediaLinkService.getAll()

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto,
    ) {

        if (message.text.isEmpty() && message.listPhoto.isEmpty()) {
            absSender.execute(createMessage(chatId, "Вы не ввели сообщение!!!"))

        } else if (message.text.isNotEmpty() && message.listPhoto.isEmpty()) {
            val text = getTextMessage(absSender, chatId, message, TEXT_SIZE)
            absSender.execute(
                createTextDialogMenu(
                    chatId = chatId,
                    text = text,
                    inlineButtons = listOf(
                        listOf("$callbackSend|send" to "Отправить пост"),
                        listOf("$callbackBack|back" to "Назад"),
                    ),
                )
            )
        } else if (message.listPhoto.isNotEmpty()) {
            val text = getTextMessage(absSender, chatId, message, TEXT_PHOTO_SIZE)
            message.text = text
            createPhotosMessage(
                absSender = absSender,
                chatId = chatId,
                message = message,
                inlineButtons = listOf(
                    listOf("$callbackSend|send" to "Отправить пост"),
                    listOf("$callbackBack|back" to "Назад"),
                ),
            )

        }
    }


    private fun getTextMessage(absSender: AbsSender, chatId: String, message: MessageUserDto, textSize: Int): String {
        var text = String()
        text = if (message.link == "") {
            previewMessage(message)
        } else {
            previewMessageAndLinks(message)
        }

        if (text.length <= textSize) return text
        else {
            absSender.execute(
                createMessage(
                    chatId, "Текст слишком длинный! " +
                            "Максимальное количество символов должно составлять -> $textSize, а Ваше сообщение содержит ${text.length}. " +
                            "Удалите, пожалуйста ${text.length - textSize} символ(а, ов), или составте другое сообщение."
                )
            )
            text = ""
            return text
        }
    }
}