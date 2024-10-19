package com.example.demo_bot.view.handler.changeData.exchange

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreateExchangeCallbackHandler : ChangeDataCallbackHandler {

    val callbackNext = ChangeDataHandlerName.CREATE_EXCHANGE_SKETCH.text
    val callbackBack = ChangeDataHandlerName.CRUD_MENU_EXCHANGE.text

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CREATE_EXCHANGE

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, argument: String) {

       val text =
            "название биржи"+
            "реферальную ссылку на аккаунт"+
            "реферальный код"

        absSender.execute(
            createTextDialogMenu(
                chatId = chatId,
                text = text,
                inlineButtons = listOf(
                    listOf("$callbackNext|${name.text}" to "Далее"),
                    listOf(callbackBack to "Назад"),
                ),
            )
        )



//            ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> {
//                text = "Вы не можете создать новые HashTags," +
//                        " т.к. они создаются для конкретных разделов создания сообщений," +
//                        " а создать новый раздел можно только программно! Попробуйте выбрать другую функцию. "
//
//            }

//            ChangeDataHandlerName.CHANGE_GAME.text ->
//                text = getSampleText(
//                    "название игры",
//                    "реферальная ссылка на игру",
//                    "реферальная ссылка на Ваш клан (если такой имеется)"
//                )
//
//            ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text -> text = getSampleText(
//                "название соц. сети",
//                "ссылка на аккаунт",
//                ""
//            )
    }



}