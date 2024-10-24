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
            changeDataModel.attributes.attribute1 == ""
            && changeDataModel.exchange.name == ""
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
                        chatId, "Сообщение введено неверно!!! \n<b>Пример:</b>\n" +
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

        var text = ""
        when (operation) {

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (attributes.id != null) {
                    val hashTag = attributesService.getById(attributes.id!!)

                    text = """
                <b>Старые данные:</b>
                <b>Название ХешТега</b> => ${hashTag.name}
                <b>Атрибут 1</b> => ${hashTag.attribute1}
                <b>Атрибут 2</b> => ${hashTag.attribute2}
                <b>Атрибут 3</b> => ${hashTag.attribute3}
                <b>Атрибут 4</b> => ${hashTag.attribute4}
                <b>Атрибут 5</b> => ${hashTag.attribute5}
                                        
                <b>Новые данные:</b>
                <b>Название ХешТега</b> => ${hashTag.name}
                <b>Атрибут 1</b> => ${attributes.attribute1}
                <b>Атрибут 2</b> => ${attributes.attribute2}
                <b>Атрибут 3</b> => ${attributes.attribute3}
                <b>Атрибут 4</b> => ${attributes.attribute4}
                <b>Атрибут 5</b> => ${attributes.attribute5}""".trimIndent()
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
                <b>Новые данные:</b>
                <b>Название биржи</b> => ${exchange.name}
                <b>Реферальная ссылка на аккаунт</b> => ${exchange.link}
                <b>Реферальный код</b> => ${exchange.code}""".trimIndent()
            }

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (exchange.id != null) {
                    val oldExchange = exchangeLinkService.getById(exchange.id!!)
                    text = """
                <b>Старые данные:</b>
                <b>Название биржи</b> => ${oldExchange.name}
                <b>Реферальную ссылку на аккаунт</b> => ${oldExchange.link}
                <b>Реферальный код</b> => ${oldExchange.code}
                        
                <b>Новые данные:</b>
                <b>Название биржи</b> => ${exchange.name}
                <b>Реферальную ссылку на аккаунт</b> => ${exchange.link}
                <b>Реферальный код</b> => ${exchange.code}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (exchange.id != null) {
                    val oldExchange = exchangeLinkService.getById(exchange.id!!)
                    text = """
                <b>Удалить данные:</b>
                <b>Название биржи</b> => ${oldExchange.name}
                <b>Реферальную ссылку на аккаунт</b> => ${oldExchange.link}
                <b>Реферальный код</b> => ${oldExchange.code}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }

    private fun getGameTextForSketch(operation: String, game: GameLinkDto): String {
        var text = ""
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> text = """
                <b>Новые данные:</b>
                <b>Название игры</b> => ${game.name}
                <b>Реферальная ссылка на игру</b> => ${game.link}
                <b>Ссылка на наш Клан</b> => ${game.clanLink}""".trimIndent()

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (game.id != null) {
                    val oldGame = gameLinkService.getById(game.id!!)
                    text = """
                <b>Старые данные:</b>
                <b>Название игры</b> => ${oldGame.name}
                <b>Реферальная ссылка на игру</b> => ${oldGame.link}
                <b>Ссылка на наш Клан</b> => ${oldGame.name}
                        
                <b>Новые данные:</b>
                <b>Название игры</b> => ${game.name}
                <b>Реферальная ссылка на игру</b> => ${game.link}
                <b>Ссылка на наш Клан</b> => ${game.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (game.id != null) {
                    val oldGame = gameLinkService.getById(game.id!!)
                    text = """
                <b>Удалить данные:</b>
                <b>Название игры</b> => ${oldGame.name}
                <b>Реферальная ссылка на игру</b> => ${oldGame.link}
                <b>Ссылка на наш Клан</b> => ${oldGame.name}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }

    private fun getSocialMediaTextForSketch(operation: String, socialMedia: SocialMediaLinkDto): String {
        var text = ""
        when (operation) {
            ChangeDataHandlerName.CREATE_DATA.text -> text = """
                <b>Новые данные:</b>
                <b>Название Социальной сети</b> => ${socialMedia.name}
                <b>Ссылка на Социальную сеть</b> => ${socialMedia.link}""".trimIndent()

            ChangeDataHandlerName.UPDATE_DATA.text -> {
                if (socialMedia.id != null) {
                    val oldSocialLink = socialMediaLinkService.getById(socialMedia.id!!)
                    text = """
                <b>Старые данные:</b>
                <b>Название Социальной сети</b> => ${oldSocialLink.name}
                <b>Ссылка на Социальную сеть</b> => ${oldSocialLink.link}                
                        
                <b>Новые данные:</b>
                <b>Название Социальной сети</b> => ${socialMedia.name}
                <b>Ссылка на Социальную сеть</b> => ${socialMedia.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }

            ChangeDataHandlerName.DELETE_DATA.text -> {
                if (socialMedia.id != null) {
                    val oldSocialLink = socialMediaLinkService.getById(socialMedia.id!!)
                    text = """
                <b>Удалить данные:</b>
                <b>Название Социальной сети</b> => ${oldSocialLink.name}
                <b>Ссылка на Социальную сеть</b> => ${oldSocialLink.link}""".trimIndent()
                } else text = ERROR_DATA_ID
            }
        }
        return text
    }
}