package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.MessageUser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender


@Component
class CreateMessageHandler : CreatePostCallbackHandler {

    override val name: CreatePostHandlerName = CreatePostHandlerName.CREATE_MESSAGE

    val callbackNext = CreatePostHandlerName.MESSAGE_SKETCH.text
    val callbackBack = CreatePostHandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser,
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Введите текс сообщения",
                listOf(
                    listOf("$callbackNext|next" to "Далее"),
                    listOf("$callbackBack|back" to "Назад"),
                ),
            )
        )
    }
}