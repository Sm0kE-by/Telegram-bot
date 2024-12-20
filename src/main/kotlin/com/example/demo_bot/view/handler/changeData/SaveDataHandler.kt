package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.service.dto.AttributesDto
import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.dto.GameLinkDto
import com.example.demo_bot.service.dto.SocialMediaLinkDto
import com.example.demo_bot.service.interfaces.AttributesService
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SaveDataHandler(
    private val exchangeLinkService: ExchangeLinkService,
    private val attributesService: AttributesService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val messageService: MessageService
) : ChangeDataHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.SAVE_DATA

    val callbackDone = CreatePostHandlerName.START_HANDLER.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {
        changingDataInTheDatabase(changeDataModel)
        absSender.execute(
            createTextDialogMenu(
                chatId = chatId,
                text = messageService.getMessage("changeDate.askSaveDataHandler"),
                inlineButtons = listOf(
                    listOf(callbackDone to messageService.getMessage("button.Done")),
                ),
            )
        )
    }

    private fun changingDataInTheDatabase(changeDataModel: ChangeDataModel) {

        when (changeDataModel.category) {
            ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> changingAttributes(
                changeDataModel.operation,
                changeDataModel.attributes
            )

            ChangeDataHandlerName.CHANGE_EXCHANGE.text ->
                //TODO добавить реферальный код в проверку
                changingExchange(
                    changeDataModel.operation,
                    changeDataModel.exchange
                )

            ChangeDataHandlerName.CHANGE_GAME.text -> changingGame(
                changeDataModel.operation, changeDataModel.game
            )

            ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text -> changingSocialMedia(
                changeDataModel.operation,
                changeDataModel.socialLink
            )
        }
    }

    private fun changingAttributes(operation: String, attributes: AttributesDto) {
        when (operation) {
            ChangeDataHandlerName.UPDATE_DATA.text -> attributesService.update(attributes.id!!, attributes)
        }
    }

    private fun changingExchange(operation: String, exchange: ExchangeLinkDto) {
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> exchangeLinkService.create(exchange)
            ChangeDataHandlerName.UPDATE_DATA.text -> exchangeLinkService.update(exchange.id!!, exchange)
            ChangeDataHandlerName.DELETE_DATA.text -> exchangeLinkService.delete(exchange.id!!)
        }
    }

    private fun changingGame(operation: String, game: GameLinkDto) {
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> gameLinkService.create(game)
            ChangeDataHandlerName.UPDATE_DATA.text -> gameLinkService.update(game.id!!, game)
            ChangeDataHandlerName.DELETE_DATA.text -> gameLinkService.delete(game.id!!)
        }
    }

    private fun changingSocialMedia(operation: String, socialMedia: SocialMediaLinkDto) {
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> socialMediaLinkService.create(socialMedia)
            ChangeDataHandlerName.UPDATE_DATA.text -> socialMediaLinkService.update(socialMedia.id!!, socialMedia)
            ChangeDataHandlerName.DELETE_DATA.text -> socialMediaLinkService.delete(socialMedia.id!!)
        }
    }
}