package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.enums.ChangeDateHandlerName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class ChangeDataMenuHandler : ChangeDataCallbackHandler {

    override val name: ChangeDateHandlerName = ChangeDateHandlerName.CHANGE_DATA_MENU

    val callbackCRUDMenu = ChangeDateHandlerName.CRUD_MENU.text

    val callbackChangeAttributesMenu = ChangeDateHandlerName.CHANGE_ATTRIBUTES.text
    val callbackChangeExchangeMenu = ChangeDateHandlerName.CHANGE_EXCHANGE.text
    val callbackChangeGameMenu = ChangeDateHandlerName.CHANGE_GAME.text
    val callbackChangeSocialMediaMenu = ChangeDateHandlerName.CHANGE_SOCIAL_MEDIA.text
    val callbackBack = CreatePostHandlerName.START_HANDLER.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, fromHandler: String) {
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