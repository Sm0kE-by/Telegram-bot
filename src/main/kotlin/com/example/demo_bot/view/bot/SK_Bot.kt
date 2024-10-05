package com.example.demo_bot.view.bot

import com.example.demo_bot.service.dto.MessagePhotoDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.*
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.handler.createPost.CreatePostCallbackHandler
import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.model.ExchangeModel
import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
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
    callbackHandlers: Set<CreatePostCallbackHandler>,
    callbackHandlers2: Set<ChangeDataCallbackHandler>,
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
    private lateinit var handlerMapping: Map<String, CreatePostCallbackHandler>
    private lateinit var handlerMapping2: Map<String, ChangeDataCallbackHandler>
    // private lateinit var commands: ArrayList<BotCommand>


    init {
        commands.add(BotCommand("/start", "Start"))
        //     registerAll(*commands.toTypedArray())
        handlerMapping = callbackHandlers.associateBy { it.name.text }
        handlerMapping2 = callbackHandlers2.associateBy { it.name.text }
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
                    handlerMapping.getValue(CreatePostHandlerName.START_HANDLER.text)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            myMessage
                        )
                    //Проверка наличия текста в диалоге CREATE_MESSAGE
                } else if (update.message.hasText() && myMessage.fromHandler == CreatePostHandlerName.CREATE_MESSAGE.text) {
                    myMessage.text = update.message.text
                } else if (update.message.hasText() && myMessage.fromHandler == ChangeDataHandlerName.CREATE_DATA_MESSAGE.text) {
                    myMessage.text = update.message.text
                } else if (update.message.hasText()) {
                    execute(createMessage(chatId, "Вы написали: *${update.message.text}*"))
                    //Если просто фото (надо проверить на наличие заголовка)
                } else if (update.message.hasPhoto() && update.message.mediaGroupId == null) {
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
                    CreatePostHandlerName.START_HANDLER.text -> {}
                    ChangeDataHandlerName.CHANGE_DATA_MENU.text -> {
                        myMessage = MessageUserDto(
                            title = "",
                            text = "",
                            link = "",
                            hashTage = "",
                            socialLink = "",
                            fromHandler = "",
                            listPhoto = emptyList()
                        )
                    }

                    CreatePostHandlerName.CREATE_POST_MENU.text -> {
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
                            CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text -> myMessage.fromHandler =
                                CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text

                            CreatePostHandlerName.INVITE_NEW_GAME.text -> myMessage.fromHandler =
                                CreatePostHandlerName.INVITE_NEW_GAME.text

                            CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> myMessage.fromHandler =
                                CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text

                            CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> myMessage.fromHandler =
                                CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text
                        }
                    }

                    CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text -> {
                        myMessage.fromHandler = CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text
                        getHashTage(CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text, myMessage)
                    }

                    CreatePostHandlerName.INVITE_NEW_GAME.text -> {
                        myMessage.fromHandler = CreatePostHandlerName.INVITE_NEW_GAME.text
                        getHashTage(CreatePostHandlerName.INVITE_NEW_GAME.text, myMessage)
                    }

                    CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> {
                        myMessage.fromHandler = CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text
                        getHashTage(CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text, myMessage)
                    }

                    CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                        myMessage.fromHandler = CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text
                        getHashTage(CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text, myMessage)
                    }

                    CreatePostHandlerName.CREATE_MESSAGE.text -> {
//                        val fromHandler = HandlerName.CREATE_MESSAGE.text
                        if (callbackArguments[1] != "empty") {
                            when (myMessage.fromHandler) {
                                CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> {
                                    val dto = gameLinkService.getByName(callbackArguments[1])
                                    myMessage.link = "[${dto.name}]${dto.link} - начни играть прямо сейчас!!!"
                                }

                                CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                                    val dto = exchangeLinkService.getByName(callbackArguments[1])
                                    myMessage.link =
                                        "[${dto.name}](${dto.link}) - регистрируйся прямо сейчас и получай крутой бонус по моей реферальной ссылке!!!"
                                }
                            }
                        }
                        myMessage.fromHandler = CreatePostHandlerName.CREATE_MESSAGE.text
                    }

                    CreatePostHandlerName.MESSAGE_SKETCH.text -> {
                        myMessage.title = "[SKcrypto](https://t.me/DefiSKcrypto)"
                        myMessage.socialLink = ""
                        val dto = socialMediaLinkService.getAll()
                        dto.forEach { myMessage.socialLink += "[${it.name}](${it.link}) " }

                    }

                    //

                    ChangeDataHandlerName.CREATE_DATA_EXCHANGE.text -> {
                        myMessage.fromHandler = ChangeDataHandlerName.CREATE_DATA_MESSAGE.text
                    }
                    ChangeDataHandlerName.CHANGE_DATA_SAVE.text -> {
                        myMessage.fromHandler = ""
                        val arguments = myMessage.text.split("||")
                        val newExchange = ExchangeModel(arguments[0], arguments[1])
                        exchangeLinkService
                    }
                }

                messageUserService.update(userId.toInt(), myMessage)

                if (handlerMapping.containsKey(callbackHandlerName))
                    handlerMapping.getValue(callbackHandlerName)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            myMessage
                        )
                else if (handlerMapping2.containsKey(callbackHandlerName)) {

                    if (myMessage.fromHandler == ChangeDataHandlerName.CREATE_DATA_MESSAGE.text) {
                        handlerMapping2.getValue(callbackHandlerName)
                            .myProcessCallbackData(
                                absSender = this,
                                chatId = chatId,
                                argument = myMessage.text
                            )
                    } else handlerMapping2.getValue(callbackHandlerName)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            argument = ""
                        )
                }
            }
        }
    }

    fun getHashTage(param: String, message: MessageUserDto) {
        val dto = atrService.getByName(param)
        message.hashTage = "${dto.attribute1} ${dto.attribute2} ${dto.attribute3} ${dto.attribute4} ${dto.attribute5}"
    }
}