package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.dto.GameLinkDto
import com.example.demo_bot.service.dto.SocialMediaLinkDto
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.util.*
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class ChangeDataMessageHandler(
    private val exchangeLinkService: ExchangeLinkService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val messageService: MessageService
) : ChangeDataHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.CHANGE_DATA_MESSAGE

    val callbackNext = ChangeDataHandlerName.SKETCH_DATA.text
    val callbackBack = ChangeDataHandlerName.CHANGE_MENU.text
    val callbackBack2 = ChangeDataHandlerName.CRUD_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {
        val text = getPresentationText(changeDataModel)
        //проверка в какой хендлер возвращаться
        val backCallback =
            if (changeDataModel.operation == ChangeDataHandlerName.CREATE_DATA.text) callbackBack2 else callbackBack
        val nextCallback = if (
            changeDataModel.category == ChangeDataHandlerName.CHANGE_ATTRIBUTES.text
            && (changeDataModel.operation == ChangeDataHandlerName.CREATE_DATA.text
                    || changeDataModel.operation == ChangeDataHandlerName.DELETE_DATA.text)
        ) callbackBack2 else callbackNext
        absSender.execute(
            createTextDialogMenu(
                chatId = chatId,
                text = text,
                inlineButtons = listOf(
                    listOf(nextCallback to messageService.getMessage("button.next")),
                    listOf(backCallback to messageService.getMessage("button.back")),
                ),
            )
        )
    }

    private fun getPresentationText(changeDataModel: ChangeDataModel): String =
        when (changeDataModel.category) {
            ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> getAttributesPresentationText(changeDataModel.operation)
            ChangeDataHandlerName.CHANGE_EXCHANGE.text -> getExchangePresentationText(
                changeDataModel.operation,
                changeDataModel.exchange
            )

            ChangeDataHandlerName.CHANGE_GAME.text -> getGamePresentationText(
                changeDataModel.operation,
                changeDataModel.game
            )

            ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text -> getSocialMediaPresentationText(
                changeDataModel.operation,
                changeDataModel.socialLink
            )

            else -> ""
        }


    private fun getAttributesPresentationText(operation: String): String =
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> getAttributesCreateDeleteText()
            ChangeDataHandlerName.UPDATE_DATA.text -> getAttributesUpdateText()
            ChangeDataHandlerName.DELETE_DATA.text -> getAttributesCreateDeleteText()
            else -> ""
        }


    private fun getExchangePresentationText(operation: String, exchange: ExchangeLinkDto): String =
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> getSampleCreateUpdateText(
                messageService.getMessage("changeDate.exchangeName"),
                messageService.getMessage("changeDate.referralLinkToAccount"),
                messageService.getMessage("changeDate.referralCode")
            )

            ChangeDataHandlerName.UPDATE_DATA.text -> getSampleCreateUpdateText(
                messageService.getMessage("changeDate.exchangeName"),
                messageService.getMessage("changeDate.referralLinkToAccount"),
                messageService.getMessage("changeDate.referralCode")
            )

            ChangeDataHandlerName.DELETE_DATA.text -> {
                //TODO проверка на Налл нужна или нет
                val nameExchange = exchange.id?.let { exchangeLinkService.getById(it).name }
                getSampleDeleteText(nameExchange!!, messageService.getMessage("button.cryptoExchanges"))
            }

            else -> ""
        }


    private fun getGamePresentationText(operation: String, game: GameLinkDto): String =
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> getSampleCreateUpdateText(
                messageService.getMessage("changeDate.gameName"),
                messageService.getMessage("changeDate.referralLinkToGame"),
                messageService.getMessage("changeDate.referralLinkToClanIfThereIsOne")
            )

            ChangeDataHandlerName.UPDATE_DATA.text -> getSampleCreateUpdateText(
                messageService.getMessage("changeDate.gameName"),
                messageService.getMessage("changeDate.referralLinkToGame"),
                messageService.getMessage("changeDate.referralLinkToClanIfThereIsOne")
            )

            ChangeDataHandlerName.DELETE_DATA.text -> {
                //TODO проверка на Налл нужна или нет
                val nameGame = game.id?.let { gameLinkService.getById(it).name }
                getSampleDeleteText(nameGame!!, messageService.getMessage("button.games"))
            }

            else -> ""
        }


    private fun getSocialMediaPresentationText(operation: String, socialMedia: SocialMediaLinkDto): String =
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> getSampleCreateUpdateText(
                messageService.getMessage("changeDate.socialNetworkName"),
                messageService.getMessage("changeDate.LinkToSocialNetwork"),
            )

            ChangeDataHandlerName.UPDATE_DATA.text -> getSampleCreateUpdateText(
                messageService.getMessage("changeDate.socialNetworkName"),
                messageService.getMessage("changeDate.LinkToSocialNetwork"),
            )

            ChangeDataHandlerName.DELETE_DATA.text -> {
                //TODO проверка на Налл нужна или нет
                val nameSocialMedia = socialMedia.id?.let { socialMediaLinkService.getById(it).name }
                getSampleDeleteText(nameSocialMedia!!, messageService.getMessage("button.socialNetworks"))
            }

            else -> ""
        }


}