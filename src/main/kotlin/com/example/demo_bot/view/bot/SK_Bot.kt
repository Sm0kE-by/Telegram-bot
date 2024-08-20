package com.example.demo_bot.view.bot

import com.example.demo_bot.service.interfaces.AttributesService
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.view.handler.MyCallbackHandlerBot
import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.model.ExchangeAttributes
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.view.model.MessageModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class SK_Bot(
    commands: Set<BotCommand>,
    callbackHandlers: Set<MyCallbackHandlerBot>,
    @Value("\${telegram.token}")
    token: String,
    private val exchangeLinkService: ExchangeLinkService,
    private val atrService: AttributesService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
) : TelegramLongPollingCommandBot(token) {

    @Value("\${telegram.botName}")
    private val botName: String = ""
    private lateinit var handlerMapping: Map<String, MyCallbackHandlerBot>
    private var fromHandler: String = ""

//    private var messageToSend: String = ""

    var myMessage = MessageModel("", "", "", "", "", "")

    //    private var gameLink: String = ""
//    private val game = GameNameAttributes()
    private val exchange = ExchangeAttributes()

    //val list :List<BotCommand> = listOf(BotCommand("/start",""))
    init {
        registerAll(*commands.toTypedArray())
        handlerMapping = callbackHandlers.associateBy { it.name.text }
        //this.execute(SetMyCommands(List<BotCommand>))
    }

    override fun getBotUsername(): String = botName

    override fun processNonCommandUpdate(update: Update) {

        if (update.hasMessage()) {
            val chatId = update.message.chatId.toString()
            if (update.message.hasText() && fromHandler == HandlerName.CREATE_MESSAGE.text)
                myMessage.text = update.message.text
            else if (update.message.hasText()) {
                execute(createMessage(chatId, "Вы написали: *${update.message.text}*"))
            } else {
                execute(createMessage(chatId, "Я понимаю только текст!"))
            }
        }
        /**
         * Здесь мы добавили новую проверку hasCallbackQuery(), внутри которой извлекаем контекст callbackData.
         * Технически он представляет собой строку и вы можете поместить туда всё что угодно.
         * Это именно тот самый контекст, которого нам не хватает в функционале обычных кнопок.
         *
         * Перед началом обработки запроса нам нужно отправить клиентскому приложению AnswerCallbackQuery.
         * Мы как бы говорим, что запрос принят и мы начали его обработку. Если этого не сделать,
         * то клиент будет видеть анимацию обработки запроса и подумает, что наше приложение «зависло».
         *
         * Далее начинаем извлекать информацию из callbackData. Формат может быть любым, но при этом должен быть
         * единым для всех ваших callback-ов. Я здесь разделяю параметры вертикальной чертой,
         * причём первым параметром всегда ставлю текстовое имя из HandlerName.
         * По этому имени я извлекаю нужный обработчик из handlerMapping и передаю все остальные аргументы кроме
         * первого внутрь этого обработчика.
         */
        else if (update.hasCallbackQuery()) {


            val callbackQuery = update.callbackQuery
            val callbackData = callbackQuery.data
            // var messageToSens = ""

            val callbackQueryId = callbackQuery.id
            execute(AnswerCallbackQuery(callbackQueryId))

            val callbackArguments = callbackData.split("|")
            val callbackHandlerName = callbackArguments.first()

            //Формируем сообщение перед отправкой
//            if (callbackHandlerName == HandlerName.SEND_MESSAGE.text) {
//                //  messageToSens = myMessage
//                myMessage.text = messageToSend
//            }
            //вроде всегда 3
            //создаем ссылку на игру из ежедневного задания
//            if (callbackArguments.size == 3 && callbackArguments[2] == "daily_tasks_in_games") {
//                val link = findNameGameAndLink(callbackArguments[1],game)
//                gameLink = ""
//                gameLink= "$link - начни играть прямо сейчас!!!"
//            }
            //вроде всегда 3
            //создаем ссылку на биржу из события на криптобирже
//            if (callbackArguments.size == 3 && callbackArguments[2] == "new_event_on_crypto") {
//                val exchange = exchangeLinkService.getByName(callbackArguments[1])
//                val link = "[${exchange.name}](${exchange.link})"
//                //            val link = getExchangeName(callbackArguments[1],exchange)
//                //gameLink = ""
//                myMessage.socialLink =
//                    "$link - регистрируйся прямо сейчас и получай крутой бонус по моей реферальной ссылке!!!"
//            }

            //срабатывает всегда, если это не отправка готового сообщения
//            if (myMessage.text == "") {
//                handlerMapping.getValue(callbackHandlerName)
//                    .myProcessCallbackData(
//                        absSender = this,
//                        callbackQuery = callbackQuery,
//                        arguments = callbackArguments.subList(1, callbackArguments.size),
//                        message = myMessage,
//                        link = gameLink
//                    )
            //срабатывает при отправке готового сообщения
            //           } else {

            when (callbackHandlerName) {
                HandlerName.CHANGE_ATTRIBUTES.text -> {}
                HandlerName.CREATE_POST_MENU.text -> {
                    fromHandler = ""
                    when (callbackArguments[1]) {
                        HandlerName.CREATE_POST_ABOUT_CRYPTO.text -> myMessage.fromHandler =
                            HandlerName.CREATE_POST_ABOUT_CRYPTO.text

                        HandlerName.INVITE_NEW_GAME.text -> myMessage.fromHandler = HandlerName.INVITE_NEW_GAME.text
                        HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> myMessage.fromHandler =
                            HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text

                        HandlerName.DAILY_TASKS_IN_GAMES.text -> myMessage.fromHandler =
                            HandlerName.DAILY_TASKS_IN_GAMES.text
                    }
                }

                HandlerName.CREATE_POST_ABOUT_CRYPTO.text -> {
                    myMessage.fromHandler = HandlerName.CREATE_POST_ABOUT_CRYPTO.text
                    getHashTage(HandlerName.CREATE_POST_ABOUT_CRYPTO.text)
                }

                HandlerName.INVITE_NEW_GAME.text -> {
                    myMessage.fromHandler = HandlerName.INVITE_NEW_GAME.text
                    getHashTage(HandlerName.INVITE_NEW_GAME.text)
                }

                HandlerName.DAILY_TASKS_IN_GAMES.text -> {
                    myMessage.fromHandler = HandlerName.DAILY_TASKS_IN_GAMES.text
                    getHashTage(HandlerName.DAILY_TASKS_IN_GAMES.text)
                }

                HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                    myMessage.fromHandler = HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text
                    getHashTage(HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text)
                }

                HandlerName.CREATE_MESSAGE.text -> {
                    fromHandler = HandlerName.CREATE_MESSAGE.text
                    if (callbackArguments[1] != "empty") {
                        when (myMessage.fromHandler) {
                            HandlerName.DAILY_TASKS_IN_GAMES.text -> {
                                val dto = gameLinkService.getByName(callbackArguments[1])
                                myMessage.link = "[${dto.name}]${dto.link} - начни играть прямо сейчас!!!"
                            }

                            HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                                val dto = exchangeLinkService.getByName(callbackArguments[1])
                                myMessage.link = "[${dto.name}](${dto.link}) - регистрируйся прямо сейчас и получай крутой бонус по моей реферальной ссылке!!!"
                            }
                        }
                    }
                }

                HandlerName.MESSAGE_SKETCH.text -> {
                    myMessage.title = "[SKcrypto](https://t.me/DefiSKcrypto)"
                    val dto = socialMediaLinkService.getAll()
                    dto.forEach { myMessage.socialLink += "[${it.name}](${it.link}) " }

                }

                HandlerName.SEND_MESSAGE.text -> {}
            }



            handlerMapping.getValue(callbackHandlerName)
                .myProcessCallbackData(
                    absSender = this,
                    callbackQuery = callbackQuery,
                    myMessage
                )
        }
    }

    fun getHashTage(param: String) {
        val dto = atrService.getByName(param)
        myMessage.hashTags = "${dto.attribute1} ${dto.attribute2} ${dto.attribute3} ${dto.attribute4} ${dto.attribute5}"

    }


}