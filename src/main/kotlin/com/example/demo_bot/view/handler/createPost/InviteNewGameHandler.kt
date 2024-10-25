package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.MessageUser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class InviteNewGameHandler(private val messageService: MessageService) : CreatePostCallbackHandler {
    override val name: CreatePostHandlerName = CreatePostHandlerName.INVITE_NEW_GAME

    val callbackNext = CreatePostHandlerName.CREATE_MESSAGE.text
    val callbackBack = CreatePostHandlerName.CREATE_POST_MENU.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser,
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                messageService.getMessage("createPost.askInviteNewGameHandler"),
                listOf(
                    listOf("$callbackNext|empty" to messageService.getMessage("button.next")),
                    listOf("$callbackBack|empty" to messageService.getMessage("button.back")),
                ),
            )
        )
    }
}