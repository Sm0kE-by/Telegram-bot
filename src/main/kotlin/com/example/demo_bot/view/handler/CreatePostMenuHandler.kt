package com.example.demo_bot.view.handler

//import com.example.demo_bot.view.handler.MessageSketchHandler.Companion.createNewPost
//import com.example.demo_bot.view.handler.MessageSketchHandler.Companion.dailyTaskInGames
//import com.example.demo_bot.view.handler.MessageSketchHandler.Companion.inviteNewGame
//import com.example.demo_bot.view.handler.MessageSketchHandler.Companion.newEventOnCryptoExchange
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.createDialogMenu
import com.example.demo_bot.view.model.MessageModel
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreatePostMenuHandler: MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.CREATE_POST_MENU
   // val fromHandlerName =  HandlerName.CREATE_POST_MENU.text

    val callbackCreateNewPost = HandlerName.CREATE_POST_ABOUT_CRYPTO.text
    val callbackInviteNewGame = HandlerName.INVITE_NEW_GAME.text
    val callbackNewEventOnCryptoExchange = HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text
    val callbackDailyTaskInGames = HandlerName.DAILY_TASKS_IN_GAMES.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        message: MessageModel,
    ) {

        val chatId = callbackQuery.message.chatId.toString()

        absSender.execute(
            createDialogMenu(
                chatId,
                "Выберите действие",
                listOf(
                    listOf("$callbackCreateNewPost|${HandlerName.CREATE_POST_ABOUT_CRYPTO.text}" to "Создать пост про крипту"),
                    listOf("$callbackInviteNewGame|${HandlerName.INVITE_NEW_GAME.text}" to "Приглошение в новую игру"),
                    listOf("$callbackNewEventOnCryptoExchange|${HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text}" to "Событие на криптобирже"),
                    listOf("$callbackDailyTaskInGames|${HandlerName.DAILY_TASKS_IN_GAMES.text}" to "Ежедневные задания в играх"),
                ),
//                fromHandlerName = name
            )
        )






    }


}