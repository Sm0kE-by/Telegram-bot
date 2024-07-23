package com.example.demo_bot.handler

import com.example.demo_bot.model.HandlerName
import com.example.demo_bot.util.createDialogMenu
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

class DailyTaskInGames : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.CREATE_POST_MENU

    val callbackCreateNewPost = HandlerName.CREATE_MESSAGE.text
    val callbackInviteNewGame = HandlerName.INVITE_NEW_GAME.text
    val callbackNewEventOnCryptoExchange = HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text
    val callbackDailyTaskInGames = HandlerName.DAILY_TASKS_IN_GAMES.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {

        val chatId = callbackQuery.message.chatId.toString()

        absSender.execute(
            createDialogMenu(
                chatId,
                "Выберите действие\n",
                listOf(
                    listOf("$callbackCreateNewPost|create_new_post" to "Создать пост про крипту"),
                    listOf("$callbackInviteNewGame|invite_new_game" to "Приглошение в новую игру"),
                    listOf("$callbackNewEventOnCryptoExchange|new_event_on_crypto_exchange" to "Событие на криптобирже"),
                    listOf("$callbackDailyTaskInGames|daily_task_in_games" to "Ежедневные задания в играх"),
                )
            )
        )

    }
}