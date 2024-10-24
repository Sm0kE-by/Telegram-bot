package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.dto.GameLinkDto
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.view.model.enums.CommandName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.util.getRowsOfButton
import com.example.demo_bot.view.model.MessageUser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class DailyTaskInGames(
    private val gameLinkService: GameLinkService
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
                "Выберите игру",
                getRowsOfButton(listGameName, callbackCreateMessage, callbackBack),
            )
        )
    }
}