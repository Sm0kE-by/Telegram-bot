package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.view.model.enums.CommandName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.util.getRowsOfButton
import com.example.demo_bot.view.model.MessageUser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class DailyTaskInGamesHandler(
    private val gameLinkService: GameLinkService,
    private val messageService: MessageService,
) : CreatePostCallbackHandler {

    override val name: CreatePostHandlerName = CreatePostHandlerName.DAILY_TASKS_IN_GAMES

    val callbackBack = CommandName.START.text
    val callbackCreateMessage = CreatePostHandlerName.CREATE_MESSAGE.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser,
    ) {
        val listGameName = gameLinkService.getAll().map { it.name }

        absSender.execute(
            createTextDialogMenu(
                chatId,
                messageService.getMessage("createPost.askDailyTaskInGamesHandler"),
                getRowsOfButton(listGameName, callbackCreateMessage, callbackBack),
            )
        )
    }
}