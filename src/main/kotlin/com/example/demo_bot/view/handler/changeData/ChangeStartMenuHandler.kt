package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class ChangeStartMenuHandler(private val messageService: MessageService): ChangeDataHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CHANGE_DATA_START_MENU

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
                messageService.getMessage("changeDate.askChangeStartMenuHandler"),
                listOf(
                    listOf("$callbackCRUDMenu|$callbackChangeAttributesMenu" to messageService.getMessage("button.hashtags")),
                    listOf("$callbackCRUDMenu|$callbackChangeExchangeMenu" to messageService.getMessage("button.cryptoExchanges")),
                    listOf("$callbackCRUDMenu|$callbackChangeGameMenu" to messageService.getMessage("button.games")),
                    listOf("$callbackCRUDMenu|$callbackChangeSocialMediaMenu" to messageService.getMessage("button.socialNetworks")),
                    listOf(callbackBack to messageService.getMessage("button.back")),
                ),
            )
        )
    }
}