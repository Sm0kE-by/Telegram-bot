package com.example.demo_bot.view.bot

import com.example.demo_bot.service.dto.MessagePhotoDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.*
import com.example.demo_bot.view.handler.MyCallbackHandlerBot
import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.model.MessageModel
import com.example.demo_bot.view.model.enums.HandlerName
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault

@Component
class SK_Bot(
    //   commands: List<BotCommand>,
    callbackHandlers: Set<MyCallbackHandlerBot>,
    @Value("\${telegram.token}")
    token: String,
    commands: ArrayList<BotCommand>,
    private val exchangeLinkService: ExchangeLinkService,
    private val atrService: AttributesService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val userService: UserService,
    private val messageUserService: MessageUserService,
    private val messagePhotoService: MessagePhotoService,
) : TelegramLongPollingCommandBot(token) {

    @Value("\${telegram.botName}")
    private val botName: String = ""
    private lateinit var handlerMapping: Map<String, MyCallbackHandlerBot>
    // private lateinit var commands: ArrayList<BotCommand>


    init {
        commands.add(BotCommand("/start", "Start"))
        //     registerAll(*commands.toTypedArray())
        handlerMapping = callbackHandlers.associateBy { it.name.text }
        this.execute(SetMyCommands(commands, BotCommandScopeDefault(), null))
    }

    override fun getBotUsername(): String = botName

    override fun processNonCommandUpdate(update: Update) {


        if (update.hasMessage()) {

            val userId = update.message.from.id

            //Проверка юзера
            if (userId.toInt() == userService.getByUserById(userId.toInt()).id) {
                val myMessage = messageUserService.getMessageByUserId(userId.toInt())
                val chatId = update.message.chatId.toString()

                //Проверка команд
                if (update.message.text == "/start") {
                    myMessage.fromHandler = ""
                    handlerMapping.getValue(HandlerName.START_HANDLER.text)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            myMessage
                        )
                    //Проверка наличия текста в диалоге CREATE_MESSAGE
                } else if (update.message.hasText() && myMessage.fromHandler == HandlerName.CREATE_MESSAGE.text) {
                    myMessage.text = update.message.text
 //                   messageUserService.update(userId.toInt(), myMessage)
                    //Переделать!!!!!!!!!!!!!!!!!!!!
                } else if (update.message.hasText()) {
                    execute(createMessage(chatId, "Вы написали: *${update.message.text}*"))
                    //Если просто фото (надо проверить на наличие заголовка)
                } else if (update.message.hasPhoto()) {
                    val listPhotos = listOf(
                        MessagePhotoDto(
                            telegramFileId = update.message.photo[2].fileId,
                            fileSize = update.message.photo[2].fileSize
                        )
                    )
                    if (update.message.caption != null) myMessage.text = update.message.caption!!
                    myMessage.listPhoto = listPhotos
 //                   messageUserService.update(userId.toInt(), myMessage)
                } else if (update.message.mediaGroupId != null) {
                    val listPhoto = ArrayList<MessagePhotoDto>()
                    listPhoto.addAll(myMessage.listPhoto)
                    listPhoto.add(
                        MessagePhotoDto(
                            telegramFileId = update.message.photo[2].fileId,
                            fileSize = update.message.photo[2].fileSize
                        )
                    )
                    if (update.message.caption != null) myMessage.text = update.message.caption!!
                    myMessage.listPhoto = listPhoto
                } else {
                    execute(createMessage(chatId, "Я понимаю только текст!"))
                    execute(SendMediaGroup())
                }
                messageUserService.update(userId.toInt(), myMessage)
            }

        } else if (update.hasCallbackQuery()) {

            val userId = update.callbackQuery.from.id

            if (userId.toInt() == userService.getByUserById(userId.toInt()).id) {
                var myMessage = messageUserService.getMessageByUserId(userId.toInt())
                val callbackQuery = update.callbackQuery
                val callbackData = callbackQuery.data
                val chatId = callbackQuery.message?.chatId.toString()
                val callbackQueryId = callbackQuery.id
                //???
                execute(AnswerCallbackQuery(callbackQueryId))

                val callbackArguments = callbackData.split("|")
                val callbackHandlerName = callbackArguments.first()

                when (callbackHandlerName) {
                    HandlerName.START_HANDLER.text -> {}
                    HandlerName.CHANGE_ATTRIBUTES.text -> {}
                    HandlerName.CREATE_POST_MENU.text -> {
                        myMessage = MessageUserDto(
                            title = "",
                            text = "",
                            link = "",
                            hashTage = "",
                            socialLink = "",
                            fromHandler = "",
                            listPhoto = emptyList()
                        )
                        //                      myMessage.fromHandler = ""
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
                        getHashTage(HandlerName.CREATE_POST_ABOUT_CRYPTO.text, myMessage)
                    }

                    HandlerName.INVITE_NEW_GAME.text -> {
                        myMessage.fromHandler = HandlerName.INVITE_NEW_GAME.text
                        getHashTage(HandlerName.INVITE_NEW_GAME.text, myMessage)
                    }

                    HandlerName.DAILY_TASKS_IN_GAMES.text -> {
                        myMessage.fromHandler = HandlerName.DAILY_TASKS_IN_GAMES.text
                        getHashTage(HandlerName.DAILY_TASKS_IN_GAMES.text, myMessage)
                    }

                    HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                        myMessage.fromHandler = HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text
                        getHashTage(HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text, myMessage)
                    }

                    HandlerName.CREATE_MESSAGE.text -> {
//                        val fromHandler = HandlerName.CREATE_MESSAGE.text
                        if (callbackArguments[1] != "empty") {
                            when (myMessage.fromHandler) {
                                HandlerName.DAILY_TASKS_IN_GAMES.text -> {
                                    val dto = gameLinkService.getByName(callbackArguments[1])
                                    myMessage.link = "[${dto.name}]${dto.link} - начни играть прямо сейчас!!!"
                                }

                                HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                                    val dto = exchangeLinkService.getByName(callbackArguments[1])
                                    myMessage.link =
                                        "[${dto.name}](${dto.link}) - регистрируйся прямо сейчас и получай крутой бонус по моей реферальной ссылке!!!"
                                }
                            }
                        }
                        myMessage.fromHandler = HandlerName.CREATE_MESSAGE.text
                    }

                    HandlerName.MESSAGE_SKETCH.text -> {
                        myMessage.title = "[SKcrypto](https://t.me/DefiSKcrypto)"
                        myMessage.socialLink = ""
                        val dto = socialMediaLinkService.getAll()
                        dto.forEach { myMessage.socialLink += "[${it.name}](${it.link}) " }

                    }

                    HandlerName.SEND_MESSAGE.text -> {}
                }
                messageUserService.update(userId.toInt(), myMessage)


                handlerMapping.getValue(callbackHandlerName)
                    .myProcessCallbackData(
                        absSender = this,
                        chatId = chatId,
                        myMessage
                    )
            }
        }
    }

    fun getHashTage(param: String, message: MessageUserDto) {
        val dto = atrService.getByName(param)
        message.hashTage = "${dto.attribute1} ${dto.attribute2} ${dto.attribute3} ${dto.attribute4} ${dto.attribute5}"
    }
}