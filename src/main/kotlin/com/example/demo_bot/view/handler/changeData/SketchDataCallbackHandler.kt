package com.example.demo_bot.view.handler.changeData

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
class SketchDataCallbackHandler(
    private val exchangeLinkService: ExchangeLinkService,
    private val attributesService: AttributesService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
) : ChangeDataCallbackHandler {

    companion object {
        const val ERROR_DATA_ID = "error data id"
        const val DATA_INTEGRITY_ERROR = "data integrity error"
    }

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.SKETCH_DATA

    val callbackNext = ChangeDataHandlerName.SAVE_DATA.text
    val callbackBack = ChangeDataHandlerName.CHANGE_DATA_MESSAGE.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {

        if (
            changeDataModel.exchange.name == ""
            && changeDataModel.game.name == ""
            && changeDataModel.socialLink.name == ""
            && changeDataModel.operation != ChangeDataHandlerName.DELETE_DATA.text
        ) {
            absSender.execute(createMessage(chatId, "Вы не ввели сообщение!!!"))
        } else {
            val textMessage = getSketchText(changeDataModel)
            if (textMessage != ERROR_DATA_ID) {
                absSender.execute(
                    createTextDialogMenu(
                        chatId = chatId,
                        text = textMessage,
                        inlineButtons = listOf(
                            listOf(callbackNext to "Сохранить изменения"),
                            listOf(callbackBack to "Назад"),
                        ),
                    )
                )
            } else {
                absSender.execute(
                    createMessage(
                        chatId, "Сообщение введено неверно!!! \n*Пример:*\n" +
                                "название биржи||реферальную ссылку на аккаунт||реферальный код"
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
        //TODO сделать проверку на наличие всех заполненых полей
        var text = ""
        when (operation) {

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (attributes.id != null) {
                    val hashTag = attributesService.getById(attributes.id!!)

                    text = """
                *Старые данные:*
                *Название ХешТега* => ${hashTag.name}
                *Атрибут 1* => ${hashTag.attribute1}
                *Атрибут 2* => ${hashTag.attribute2}
                *Атрибут 3* => ${hashTag.attribute3}
                *Атрибут 4* => ${hashTag.attribute4}
                *Атрибут 5* => ${hashTag.attribute5}
                                        
                *Новые данные:*
                *Название ХешТега* => ${hashTag.name}
                *Атрибут 1* => ${attributes.attribute1}
                *Атрибут 2* => ${attributes.attribute2}
                *Атрибут 3* => ${attributes.attribute3}
                *Атрибут 4* => ${attributes.attribute4}
                *Атрибут 5* => ${attributes.attribute5}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }

    private fun getExchangeTextForSketch(operation: String, exchange: ExchangeLinkDto): String {
        var text = ""
        //TODO добавить реферальный код
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> {
                text = """
                *Новые данные:*
                *Название биржи* => ${exchange.name}
                *Реферальная ссылка на аккаунт* => ${exchange.link}
                *Реферальный код* => ${exchange.link}""".trimIndent()
            }

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (exchange.id != null) {
                    val oldExchange = exchangeLinkService.getById(exchange.id!!)
                    text = """
                *Старые данные:*
                *Название биржи* => ${oldExchange.name}
                *Реферальную ссылку на аккаунт* => ${oldExchange.link}
                *Реферальный код* => ${oldExchange.name}
                        
                *Новые данные:*
                *Название биржи* => ${exchange.name}
                *Реферальную ссылку на аккаунт* => ${exchange.link}
                *Реферальный код* => ${exchange.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (exchange.id != null) {
                    val oldExchange = exchangeLinkService.getById(exchange.id!!)
                    text = """
                *Удалить данные:*
                *Название биржи* => ${oldExchange.name}
                *Реферальную ссылку на аккаунт* => ${oldExchange.link}
                *Реферальный код* => ${oldExchange.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }

    private fun getGameTextForSketch(operation: String, game: GameLinkDto): String {
        var text = ""
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> text = """
                *Новые данные:*
                *Название игры* => ${game.name}
                *Реферальная ссылка на игру* => ${game.link}
                *Ссылка на наш Клан* => ${game.clanLink}""".trimIndent()

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (game.id != null) {
                    val oldGame = gameLinkService.getById(game.id!!)
                    text = """
                *Старые данные:*
                *Название игры* => ${oldGame.name}
                *Реферальная ссылка на игру* => ${oldGame.link}
                *Ссылка на наш Клан* => ${oldGame.name}
                        
                *Новые данные:*
                *Название игры* => ${game.name}
                *Реферальная ссылка на игру* => ${game.link}
                *Ссылка на наш Клан* => ${game.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (game.id != null) {
                    val oldGame = gameLinkService.getById(game.id!!)
                    text = """
                *Удалить данные:*
                *Название игры* => ${oldGame.name}
                *Реферальная ссылка на игру* => ${oldGame.link}
                *Ссылка на наш Клан* => ${oldGame.name}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }

    private fun getSocialMediaTextForSketch(operation: String, socialMedia: SocialMediaLinkDto): String {
        var text = ""
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> text = """
                *Новые данные:*
                *Название Социальной сети* => ${socialMedia.name}
                *Ссылка на Социальную сеть* => ${socialMedia.link}}""".trimIndent()

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (socialMedia.id != null) {
                    val oldSocialLink = socialMediaLinkService.getById(socialMedia.id!!)
                    text = """
                *Старые данные:*
                *Название Социальной сети* => ${oldSocialLink.name}
                *Ссылка на Социальную сеть* => ${oldSocialLink.link}                
                        
                *Новые данные:*
                *Название Социальной сети* => ${socialMedia.name}
                *Ссылка на Социальную сеть* => ${socialMedia.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (socialMedia.id != null) {
                    val oldSocialLink = socialMediaLinkService.getById(socialMedia.id!!)
                    text = """
                *Удалить данные:*
                *Название Социальной сети* => ${oldSocialLink.name}
                *Ссылка на Социальную сеть* => ${oldSocialLink.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }
}