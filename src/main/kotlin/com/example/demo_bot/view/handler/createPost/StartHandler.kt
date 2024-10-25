package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.MessageUser
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component

import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class StartHandler(private val messageService: MessageService) : CreatePostCallbackHandler {

    val callbackCreatePost = CreatePostHandlerName.CREATE_POST_MENU.text
    val callbackChangeAttributes = ChangeDataHandlerName.CHANGE_DATA_START_MENU.text

    override val name: CreatePostHandlerName = CreatePostHandlerName.START_HANDLER

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                messageService.getMessage("createPost.askStartHandler"),
                listOf(
                    listOf("$callbackCreatePost|create_post" to messageService.getMessage("button.createPost")),
                    listOf("$callbackChangeAttributes|change_attributes" to messageService.getMessage("button.changeData")),
                ),
            )
        )
    }
}