package com.example.demo_bot.view.handler.changeData.exchange.proba

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class ChangeStartMenuCallbackHandler: ChangeData2CallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CHANGE_DATA_START_MENU

    //    val callbackCRUDMenu = ChangeDataHandlerName.CRUD_MENU_EXCHANGE.text
    val callbackCRUDMenu = ChangeDataHandlerName.CRUD_MENU.text

    val callbackChangeAttributesMenu = ChangeDataHandlerName.CHANGE_ATTRIBUTES.text
    val callbackChangeExchangeMenu = ChangeDataHandlerName.CHANGE_EXCHANGE.text
    val callbackChangeGameMenu = ChangeDataHandlerName.CHANGE_GAME.text
    val callbackChangeSocialMediaMenu = ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text
    val callbackBack = CreatePostHandlerName.START_HANDLER.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Выберите пункт для редактирования",
                listOf(
                    listOf("$callbackCRUDMenu|$callbackChangeAttributesMenu" to "HashTags"),
                    listOf("$callbackCRUDMenu|$callbackChangeExchangeMenu" to "Криптобиржи"),
                    listOf("$callbackCRUDMenu|$callbackChangeGameMenu" to "Игры"),
                    listOf("$callbackCRUDMenu|$callbackChangeSocialMediaMenu" to "Социальные сети"),
                    listOf(callbackBack to "Назад"),
                ),
            )
        )
    }
}