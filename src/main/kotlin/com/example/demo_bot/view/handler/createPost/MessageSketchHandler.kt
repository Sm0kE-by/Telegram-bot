package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.*
import com.example.demo_bot.view.model.MessageUser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class MessageSketchHandler(private val messageService: MessageService) : CreatePostCallbackHandler {

    companion object {
        const val TEXT_SIZE = 4096
        const val TEXT_PHOTO_SIZE = 1024
    }

    override val name: CreatePostHandlerName = CreatePostHandlerName.MESSAGE_SKETCH

    val callbackSend = CreatePostHandlerName.SEND_MESSAGE.text
    val callbackBack = CreatePostHandlerName.CREATE_MESSAGE.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser,
    ) {

        if (message.text.isEmpty() && message.listPhoto.isEmpty()) {
            absSender.execute(createMessage(chatId, messageService.getMessage("createPost.notEnteredMessage")))
        } else if (message.text.isNotEmpty() && message.listPhoto.isEmpty()) {
            saveMessageTextForDb(absSender, chatId, message, TEXT_SIZE)
            absSender.execute(
                createTextDialogMenu(
                    chatId = chatId,
                    text = message.text,
                    inlineButtons = listOf(
                        listOf("$callbackSend|send" to messageService.getMessage("button.sendPost")),
                        listOf("$callbackBack|back" to messageService.getMessage("button.back")),
                    ),
                )
            )
        } else if (message.listPhoto.isNotEmpty()) {
            saveMessageTextForDb(absSender, chatId, message, TEXT_PHOTO_SIZE)
            createPhotosMessage(
                absSender = absSender,
                chatId = chatId,
                message = message,
                inlineButtons = listOf(
                    listOf("$callbackSend|send" to messageService.getMessage("button.sendPost")),
                    listOf("$callbackBack|back" to messageService.getMessage("button.back")),
                ),
            )

        }
    }

    private fun saveMessageTextForDb(absSender: AbsSender, chatId: String, message: MessageUser, textSize: Int) {
        val text = getTextMessage(absSender, chatId, message, textSize)
        message.text = text
    }

    private fun getTextMessage(absSender: AbsSender, chatId: String, message: MessageUser, textSize: Int): String {

        var text = if (message.link == "") {
            previewMessage(message)
        } else {
            previewMessageAndLinks(message)
        }

        if (text.length <= textSize) return text
        else {
            absSender.execute(
                createMessage(
                    chatId,
//                    "Текст слишком длинный! " +
//                            "Максимальное количество символов должно составлять -> $textSize, а Ваше сообщение содержит ${text.length}. " +
//                            "Удалите, пожалуйста ${text.length - textSize} символ(а, ов), или составте другое сообщение."
                    messageService.getMessage(
                        "createPost.askTextIsTooLong.regexp",
                        textSize,
                        text.length,
                        text.length - textSize
                    )
                )
            )
            text = ""
            return text
        }
    }
}
