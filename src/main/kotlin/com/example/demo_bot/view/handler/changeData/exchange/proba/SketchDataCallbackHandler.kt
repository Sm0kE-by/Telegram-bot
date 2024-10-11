package com.example.demo_bot.view.handler.changeData.exchange.proba

import com.example.demo_bot.service.dto.AttributesDto
import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.dto.GameLinkDto
import com.example.demo_bot.service.dto.SocialMediaLinkDto
import com.example.demo_bot.service.interfaces.AttributesService
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.util.createTextDialogMenu
import com.example.demo_bot.view.learn_bot.createMessage
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
) : ChangeData2CallbackHandler {

    override val name: ChangeDataHandlerName = ChangeDataHandlerName.SKETCH_DATA

    val callbackNext = ChangeDataHandlerName.SAVE_DATA.text
    val callbackBack = ChangeDataHandlerName.CHANGE_DATA_MESSAGE.text

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, changeDataModel: ChangeDataModel) {

        if (
            changeDataModel.exchange.name == ""
            && changeDataModel.game.name == ""
            && changeDataModel.socialLink.name == ""
        ) {
            absSender.execute(createMessage(chatId, "Вы не ввели сообщение!!!"))
        } else {
            if (changeDataModel.exchange.name != null && changeDataModel.exchange.link != null) {
                absSender.execute(
                    createTextDialogMenu(
                        chatId = chatId,
                        text = getSketchText(changeDataModel),
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
            ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> text =
                getAttributesTextForSketch(changeDataModel.operation, changeDataModel.attributes)

            ChangeDataHandlerName.CHANGE_EXCHANGE.text -> text =
                getExchangeTextForSketch(changeDataModel.operation, changeDataModel.exchange)

            ChangeDataHandlerName.CHANGE_GAME.text -> text =
                getGameTextForSketch(changeDataModel.operation, changeDataModel.game)

            ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text -> text =
                getSocialMediaTextForSketch(changeDataModel.operation, changeDataModel.socialLink)
        }

        return text
    }

    private fun getAttributesTextForSketch(operation: String, attributes: AttributesDto): String {
        //TODO сделать проверку на наличие всех заполненых полей
        var text = ""
        when (operation) {

            ChangeDataHandlerName.UPDATE.text -> {
                val hashTag = attributes.id?.let { attributesService.getById(it) }
                text = """
                *Старые данные:*
                *Название ХешТега* => ${hashTag?.name}
                *Атрибут 1* => ${hashTag?.attribute1}
                *Атрибут 2* => ${hashTag?.attribute2}
                *Атрибут 3* => ${hashTag?.attribute3}
                *Атрибут 4* => ${hashTag?.attribute4}
                *Атрибут 5* => ${hashTag?.attribute5}
                                        
                *Новые данные:*
                *Название ХешТега* => ${hashTag?.name}
                *Атрибут 1* => ${attributes.attribute1}
                *Атрибут 2* => ${attributes.attribute2}
                *Атрибут 3* => ${attributes.attribute3}
                *Атрибут 4* => ${attributes.attribute4}
                *Атрибут 5* => ${attributes.attribute5}""".trimIndent()
            }

            ChangeDataHandlerName.DELETE.text -> {
                val hashTag = attributes.id?.let { attributesService.getById(it) }
                text = """
                *Удалить данные:*
                *Название ХешТега* => ${hashTag?.name}
                *Атрибут 1* => ${attributes.attribute1}
                *Атрибут 2* => ${attributes.attribute2}
                *Атрибут 3* => ${attributes.attribute3}
                *Атрибут 4* => ${attributes.attribute4}
                *Атрибут 5* => ${attributes.attribute5}""".trimIndent()
            }
        }
        return text
    }

    private fun getExchangeTextForSketch(operation: String, exchange: ExchangeLinkDto): String {
        var text = ""
        //TODO добавить реферальный код
        if (exchange.name != "" && exchange.link != "") {
            when (operation) {
                ChangeDataHandlerName.CREATE.text -> {
                    text = """
                *Новые данные:*
                *Название биржи* => ${exchange.name}
                *Реферальная ссылка на аккаунт* => ${exchange.link}
                *Реферальный код* => ${exchange.link}""".trimIndent()
                }

                ChangeDataHandlerName.UPDATE.text -> {
                    if (exchange.id != null) {
                        val oldExchange = exchange.id?.let { exchangeLinkService.getById(it)
                        text = """
                *Старые данные:*
                *Название биржи* => ${oldExchange?.name}
                *Реферальную ссылку на аккаунт* => ${oldExchange?.link}
                *Реферальный код* => ${oldExchange?.name}
                        
                *Новые данные:*
                *Название биржи* => ${exchange.name}
                *Реферальную ссылку на аккаунт* => ${exchange.link}
                *Реферальный код* => ${exchange.link}""".trimIndent()
                    }
                }
                ChangeDataHandlerName.DELETE.text -> {
                    val oldExchange = exchange.id?.let { exchangeLinkService.getById(it) }
                    text = """
                *Удалить данные:*
                *Название биржи* => ${oldExchange?.name}
                *Реферальную ссылку на аккаунт* => ${oldExchange?.link}
                *Реферальный код* => ${oldExchange?.link}""".trimIndent()
                }
            }
        }
        return text
    }

    private fun getGameTextForSketch(operation: String, game: GameLinkDto): String {
        var text = ""
        when (operation) {
            ChangeDataHandlerName.CREATE.text -> text = """
                *Новые данные:*
                *Название игры* => ${game.name}
                *Реферальная ссылка на игру* => ${game.link}
                *Ссылка на наш Клан* => ${game.clanLink}""".trimIndent()

            ChangeDataHandlerName.UPDATE.text -> {
                val oldGame = game.id?.let { gameLinkService.getById(it) }
                text = """
                *Старые данные:*
                *Название игры* => ${oldGame?.name}
                *Реферальная ссылка на игру* => ${oldGame?.link}
                *Ссылка на наш Клан* => ${oldGame?.name}
                        
                *Новые данные:*
                *Название игры* => ${game.name}
                *Реферальная ссылка на игру* => ${game.link}
                *Ссылка на наш Клан* => ${game.link}""".trimIndent()
            }

            ChangeDataHandlerName.DELETE.text -> {
                val oldGame = game.id?.let { gameLinkService.getById(it) }
                text = """
                *Удалить данные:*
                *Название игры* => ${oldGame?.name}
                *Реферальная ссылка на игру* => ${oldGame?.link}
                *Ссылка на наш Клан* => ${oldGame?.name}""".trimIndent()
            }
        }
        return text
    }

    private fun getSocialMediaTextForSketch(operation: String, socialMedia: SocialMediaLinkDto): String {
        var text = ""
        when (operation) {
            ChangeDataHandlerName.CREATE.text -> text = """
                *Новые данные:*
                *Название Социальной сети* => ${socialMedia.name}
                *Ссылка на Социальную сеть* => ${socialMedia.link}}""".trimIndent()

            ChangeDataHandlerName.UPDATE.text -> {
                val oldSocialLink = socialMedia.id?.let { socialMediaLinkService.getById(it) }
                text = """
                *Старые данные:*
                *Название Социальной сети* => ${oldSocialLink?.name}
                *Ссылка на Социальную сеть* => ${oldSocialLink?.link}                
                        
                *Новые данные:*
                *Название Социальной сети* => ${socialMedia.name}
                *Ссылка на Социальную сеть* => ${socialMedia.link}""".trimIndent()
            }

            ChangeDataHandlerName.DELETE.text -> {
                val oldSocialLink = socialMedia.id?.let { socialMediaLinkService.getById(it) }
                text = """
                *Удалить данные:*
                *Название Социальной сети* => ${oldSocialLink?.name}
                *Ссылка на Социальную сеть* => ${oldSocialLink?.link}""".trimIndent()
            }
        }
        return text
    }
}