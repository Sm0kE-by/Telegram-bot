package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.MessageUser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreatePostMenuHandler(private val messageService: MessageService): CreatePostCallbackHandler {

    override val name: CreatePostHandlerName = CreatePostHandlerName.CREATE_POST_MENU

    val callbackCreateNewPost = CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text
    val callbackInviteNewGame = CreatePostHandlerName.INVITE_NEW_GAME.text
    val callbackNewEventOnCryptoExchange = CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text
    val callbackDailyTaskInGames = CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text
    val callbackBack = CreatePostHandlerName.START_HANDLER.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUser,
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                messageService.getMessage("createPost.askCreatePostMenuHandler"),
                listOf(
                    listOf("$callbackCreateNewPost|${CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text}" to messageService.getMessage("button.createPostAboutCrypto")),
                    listOf("$callbackInviteNewGame|${CreatePostHandlerName.INVITE_NEW_GAME.text}" to messageService.getMessage("button.inviteNewGame")),
                    listOf("$callbackNewEventOnCryptoExchange|${CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text}" to messageService.getMessage("button.newEventOnCryptoExchange")),
                    listOf("$callbackDailyTaskInGames|${CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text}" to messageService.getMessage("button.dailyTaskInGames")),
                    listOf("$callbackBack|${CreatePostHandlerName.START_HANDLER.text}" to messageService.getMessage("button.back")),
                ),
            )
        )
    }
}