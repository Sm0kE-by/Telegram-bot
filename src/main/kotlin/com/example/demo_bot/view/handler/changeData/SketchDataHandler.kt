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
import com.example.demo_bot.util.createMessage
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SketchDataHandler(
    private val exchangeLinkService: ExchangeLinkService,
    private val attributesService: AttributesService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val messageService: MessageService
) : ChangeDataHandler {

    companion object {
        const val ERROR_DATA_ID = "error data id"
        const val DATA_INTEGRITY_ERROR = "data integrity error"
    }

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.SKETCH_DATA

    val callbackNext = ChangeDataHandlerName.SAVE_DATA.text
    val callbackBack = ChangeDataHandlerName.CRUD_MENU.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {

        if (
            changeDataModel.attributes.attribute1 == ""
            && changeDataModel.exchange.name == ""
            && changeDataModel.game.name == ""
            && changeDataModel.socialLink.name == ""
            && changeDataModel.operation != ChangeDataHandlerName.DELETE_DATA.text
        ) {
            absSender.execute(createMessage(chatId, messageService.getMessage("createPost.notEnteredMessage")))
        } else {
            val textMessage = getSketchText(changeDataModel)
            if (textMessage != ERROR_DATA_ID && textMessage != DATA_INTEGRITY_ERROR) {
                absSender.execute(
                    createTextDialogMenu(
                        chatId = chatId,
                        text = textMessage,
                        inlineButtons = listOf(
                            listOf(callbackNext to messageService.getMessage("button.saveChanges")),
                            listOf(callbackBack to messageService.getMessage("button.back")),
                        ),
                    )
                )
            } else {
                absSender.execute(
                    createMessage(
                        chatId, messageService.getMessage("changeDate.messageWasEnteredIncorrectly")
                    )
                )
            }
        }
    }

    private fun getSketchText(changeDataModel: ChangeDataModel): String {
        var text = ""

        when (changeDataModel.category) {
            ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> {
                text = if (changeDataModel.attributes.attribute1 != ""
                    || changeDataModel.operation == ChangeDataHandlerName.DELETE_DATA.text
                )
                    getAttributesTextForSketch(changeDataModel.operation, changeDataModel.attributes)
                else DATA_INTEGRITY_ERROR
            }

            ChangeDataHandlerName.CHANGE_EXCHANGE.text -> {
                //TODO //TODO добавить реферальный код в проверку
                text = if (changeDataModel.exchange.link != ""
                    || changeDataModel.operation == ChangeDataHandlerName.DELETE_DATA.text
                )
                    getExchangeTextForSketch(changeDataModel.operation, changeDataModel.exchange)
                else DATA_INTEGRITY_ERROR
            }

            ChangeDataHandlerName.CHANGE_GAME.text ->
                text = if (changeDataModel.game.link != ""
                    || changeDataModel.operation == ChangeDataHandlerName.DELETE_DATA.text
                )
                    getGameTextForSketch(changeDataModel.operation, changeDataModel.game)
                else DATA_INTEGRITY_ERROR

            ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text ->
                text = if (changeDataModel.socialLink.link != ""
                    || changeDataModel.operation == ChangeDataHandlerName.DELETE_DATA.text
                )
                    getSocialMediaTextForSketch(changeDataModel.operation, changeDataModel.socialLink)
                else DATA_INTEGRITY_ERROR
        }

        return text
    }

    private fun getAttributesTextForSketch(operation: String, attributes: AttributesDto): String {

        var text = ""
        when (operation) {

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (attributes.id != null) {
                    val hashTag = attributesService.getById(attributes.id!!)

                    text = """
                <b>${messageService.getMessage("changeDate.oldData")}</b>
                <b>${messageService.getMessage("changeDate.hashtagName")}</b> => ${hashTag.name}
                <b>${messageService.getMessage("changeDate.attribute1")}</b> => ${hashTag.attribute1}
                <b>${messageService.getMessage("changeDate.attribute2")}</b> => ${hashTag.attribute2}
                <b>${messageService.getMessage("changeDate.attribute3")}</b> => ${hashTag.attribute3}
                <b>${messageService.getMessage("changeDate.attribute4")}</b> => ${hashTag.attribute4}
                <b>${messageService.getMessage("changeDate.attribute5")}</b> => ${hashTag.attribute5}
                                        
                <b>${messageService.getMessage("changeDate.newData")}</b>
                <b>${messageService.getMessage("changeDate.hashtagName")}</b> => ${hashTag.name}
                <b>${messageService.getMessage("changeDate.attribute1")}</b> => ${attributes.attribute1}
                <b>${messageService.getMessage("changeDate.attribute2")}</b> => ${attributes.attribute2}
                <b>${messageService.getMessage("changeDate.attribute3")}</b> => ${attributes.attribute3}
                <b>${messageService.getMessage("changeDate.attribute4")}</b> => ${attributes.attribute4}
                <b>${messageService.getMessage("changeDate.attribute5")}</b> => ${attributes.attribute5}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }

    private fun getExchangeTextForSketch(operation: String, exchange: ExchangeLinkDto): String {

        var text = ""

        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> {
                text = """
                <b>${messageService.getMessage("changeDate.newData")}</b>
                <b>${messageService.getMessage("changeDate.exchangeName")}</b> => ${exchange.name}
                <b>${messageService.getMessage("changeDate.referralLinkToAccount")}</b> => ${exchange.link}
                <b>${messageService.getMessage("changeDate.referralCode")}</b> => ${exchange.code}""".trimIndent()
            }

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (exchange.id != null) {
                    val oldExchange = exchangeLinkService.getById(exchange.id!!)
                    text = """
                <b>${messageService.getMessage("changeDate.oldData")}</b>
                <b>${messageService.getMessage("changeDate.exchangeName")}</b> => ${oldExchange.name}
                <b>${messageService.getMessage("changeDate.referralLinkToAccount")}</b> => ${oldExchange.link}
                <b>${messageService.getMessage("changeDate.referralCode")}</b> => ${oldExchange.code}
                        
                <b>${messageService.getMessage("changeDate.newData")}</b>
                <b>${messageService.getMessage("changeDate.exchangeName")}</b> => ${exchange.name}
                <b>${messageService.getMessage("changeDate.referralLinkToAccount")}</b> => ${exchange.link}
                <b>${messageService.getMessage("changeDate.referralCode")}</b> => ${exchange.code}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (exchange.id != null) {
                    val oldExchange = exchangeLinkService.getById(exchange.id!!)
                    text = """
                <b>${messageService.getMessage("changeDate.deleteData")}</b>
                <b>${messageService.getMessage("changeDate.exchangeName")}</b> => ${oldExchange.name}
                <b>${messageService.getMessage("changeDate.referralLinkToAccount")}</b> => ${oldExchange.link}
                <b>${messageService.getMessage("changeDate.referralCode")}</b> => ${oldExchange.code}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }

    private fun getGameTextForSketch(operation: String, game: GameLinkDto): String {
        var text = ""
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> text = """
                <b>${messageService.getMessage("changeDate.newData")}</b>
                <b>${messageService.getMessage("changeDate.gameName")}</b> => ${game.name}
                <b>${messageService.getMessage("changeDate.referralLinkToGame")}</b> => ${game.link}
                <b>${messageService.getMessage("changeDate.referralLinkToClan")}</b> => ${game.clanLink}""".trimIndent()

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (game.id != null) {
                    val oldGame = gameLinkService.getById(game.id!!)
                    text = """
                <b>${messageService.getMessage("changeDate.oldData")}</b>
                <b>${messageService.getMessage("changeDate.gameName")}</b> => ${oldGame.name}
                <b>${messageService.getMessage("changeDate.referralLinkToGame")}</b> => ${oldGame.link}
                <b>${messageService.getMessage("changeDate.referralLinkToClan")}</b> => ${oldGame.name}
                        
                <b>${messageService.getMessage("changeDate.newData")}</b>
                <b>${messageService.getMessage("changeDate.gameName")}</b> => ${game.name}
                <b>${messageService.getMessage("changeDate.referralLinkToGame")}</b> => ${game.link}
                <b>${messageService.getMessage("changeDate.referralLinkToClan")}</b> => ${game.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (game.id != null) {
                    val oldGame = gameLinkService.getById(game.id!!)
                    text = """
                <b>${messageService.getMessage("changeDate.deleteData")}</b>
                <b>${messageService.getMessage("changeDate.gameName")}</b> => ${oldGame.name}
                <b>${messageService.getMessage("changeDate.referralLinkToGame")}</b> => ${oldGame.link}
                <b>${messageService.getMessage("changeDate.referralLinkToClan")}</b> => ${oldGame.name}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }

    private fun getSocialMediaTextForSketch(operation: String, socialMedia: SocialMediaLinkDto): String {
        var text = ""
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> text = """
                <b>${messageService.getMessage("changeDate.newData")}</b>
                <b>${messageService.getMessage("changeDate.socialNetworkName")}</b> => ${socialMedia.name}
                <b>${messageService.getMessage("changeDate.LinkToSocialNetwork")}</b> => ${socialMedia.link}""".trimIndent()

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (socialMedia.id != null) {
                    val oldSocialLink = socialMediaLinkService.getById(socialMedia.id!!)
                    text = """
                <b>${messageService.getMessage("changeDate.oldData")}</b>
                <b>${messageService.getMessage("changeDate.socialNetworkName")}</b> => ${oldSocialLink.name}
                <b>${messageService.getMessage("changeDate.LinkToSocialNetwork")}</b> => ${oldSocialLink.link}                
                        
                <b>${messageService.getMessage("changeDate.newData")}</b>
                <b>${messageService.getMessage("changeDate.socialNetworkName")}</b> => ${socialMedia.name}
                <b>${messageService.getMessage("changeDate.LinkToSocialNetwork")}</b> => ${socialMedia.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (socialMedia.id != null) {
                    val oldSocialLink = socialMediaLinkService.getById(socialMedia.id!!)
                    text = """
                <b>${messageService.getMessage("changeDate.deleteData")}</b>
                <b>${messageService.getMessage("changeDate.socialNetworkName")}</b> => ${oldSocialLink.name}
                <b>${messageService.getMessage("changeDate.LinkToSocialNetwork")}</b> => ${oldSocialLink.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }
}