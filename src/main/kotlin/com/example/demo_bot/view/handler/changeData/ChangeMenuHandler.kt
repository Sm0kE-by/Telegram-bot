package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.service.interfaces.AttributesService
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.util.*
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class ChangeMenuHandler(
    private val attributesService: AttributesService,
    private val exchangeLinkService: ExchangeLinkService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val messageService: MessageService
) : ChangeDataHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CHANGE_MENU

    val callbackNext = ChangeDataHandlerName.CHANGE_DATA_MESSAGE.text
    val callbackBack = ChangeDataHandlerName.CRUD_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {

        absSender.execute(
            createTextDialogMenu(
                chatId,
                messageService.getMessage("changeDate.askChangeMenuHandler"),
                getPresentationText(changeDataModel, callbackNext, callbackBack),
            )
        )
    }

    private fun getPresentationText(
        changeDataModel: ChangeDataModel,
        callbackNext: String,
        callbackBack: String
    ): List<List<Pair<String, String>>> =

        when (changeDataModel.category) {
            ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> {
                val lists = attributesService.getAll().map { it.name }
                getRowsOfButton(lists, callbackNext, callbackBack)
            }

            ChangeDataHandlerName.CHANGE_EXCHANGE.text -> {
                val lists = exchangeLinkService.getAll().map { it.name }
                getRowsOfButton(lists, callbackNext, callbackBack)
            }

            ChangeDataHandlerName.CHANGE_GAME.text -> {
                val lists = gameLinkService.getAll().map { it.name }
                getRowsOfButton(lists, callbackNext, callbackBack)
            }

            ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text -> {
                val lists = socialMediaLinkService.getAll().map { it.name }
                getRowsOfButton(lists, callbackNext, callbackBack)
            }

            else -> emptyList()
        }

}