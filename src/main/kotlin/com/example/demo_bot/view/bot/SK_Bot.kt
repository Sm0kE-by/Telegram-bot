package com.example.demo_bot.view.bot

import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.dto.MessagePhotoDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.*
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.handler.changeData.exchange.proba.ChangeData2CallbackHandler
import com.example.demo_bot.view.handler.createPost.CreatePostCallbackHandler
import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.model.ChangeDataModel
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
//    callbackHandlers2: Set<ChangeDataCallbackHandler>,
    callbackHandlers2: Set<ChangeData2CallbackHandler>,
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

    //   private lateinit var handlerMapping2: Map<String, ChangeDataCallbackHandler>
    private lateinit var handlerMapping2: Map<String, ChangeData2CallbackHandler>
    private val map: HashMap<Int, ChangeDataModel> = hashMapOf()


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

                if (!map.containsKey(userId.toInt())) {
                    map.put(userId.toInt(), ChangeDataModel())
                }

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
                    myMessage.text += update.message.text

                } else if (update.message.hasText() && update.message.text.contains("||")) {
                    val listArguments = update.message.text.split("||")
                    //TODO Сделать обработку ошибок
                    if (listArguments.size == 3) {
                        map[userId.toInt()]?.exchange?.name = listArguments[1]
                        map[userId.toInt()]?.exchange?.link = listArguments[2]
                    }

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

            if (!map.containsKey(userId.toInt())) {
                map.put(userId.toInt(), ChangeDataModel())
            }

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

                    ////////////////////////////////////////////////////////////////////

                    ChangeDataHandlerName.CHANGE_DATA_START_MENU.text -> {
                        map[userId.toInt()] = ChangeDataModel()
                    }

                    ChangeDataHandlerName.CRUD_MENU.text -> {
                        map[userId.toInt()]?.category = callbackArguments[1]
                    }

                    ChangeDataHandlerName.CHANGE_DATA_MESSAGE.text -> {
                        //если нажали CREATE
                        if (callbackArguments[1] == ChangeDataHandlerName.CREATE.text)
                            map[userId.toInt()]?.operation = callbackArguments[1]
                        else {
                        //если нажали UPDATE/DELETE получаю ID выбранного элемента
                            val data = exchangeLinkService.getByName(callbackArguments[1])
                            map[userId.toInt()]?.exchange?.id = data.id
                        }
                    }

                    ChangeDataHandlerName.CHANGE_MENU.text -> {
                        //если нажали UPDATE/DELETE
                        map[userId.toInt()]?.operation = callbackArguments[1]
                    }

//                    ChangeDataHandlerName.SKETCH_DATA.text -> {
//                        map[userId.toInt()]?.operation = callbackArguments[1]
//                    }

//                    ChangeDataHandlerName.SAVE_DATA.text -> {
//                        map[userId.toInt()]?.operation = callbackArguments[1]
//                    }


                    ChangeDataHandlerName.CREATE_EXCHANGE.text -> {
                        myMessage.fromHandler = ChangeDataHandlerName.CREATE_DATA_MESSAGE.text
                    }

                    ChangeDataHandlerName.UPDATE_EXCHANGE.text -> {
                        myMessage.fromHandler = ChangeDataHandlerName.CREATE_DATA_MESSAGE.text
                        val nameExchange = exchangeLinkService.getByName(callbackArguments[1])
                        myMessage.text = "${nameExchange.id}||"
                    }

                    ChangeDataHandlerName.CREATE_EXCHANGE_SAVE.text -> {
                        myMessage.fromHandler = ""
                        val arguments = myMessage.text.split("||")
                        // TODO("внести третий элемент")
                        val newExchange = ExchangeLinkDto(name = arguments[1], link = arguments[2])
                        exchangeLinkService.create(newExchange)
                    }

                    ChangeDataHandlerName.UPDATE_EXCHANGE_SAVE.text -> {
                        myMessage.fromHandler = ""
                        val arguments = myMessage.text.split("||")
                        val idExchange = arguments[0].toInt()
                        // TODO("внести третий элемент")
                        // TODO("разобраться с добавлением ИД")
                        val newExchange = ExchangeLinkDto(name = arguments[1], link = arguments[2])
                        exchangeLinkService.update(idExchange, newExchange)
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
                                //  argument = myMessage.text
                                changeDataModel = map.getValue(userId.toInt())
                            )
                    } else handlerMapping2.getValue(callbackHandlerName)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            //  argument = ""
                            changeDataModel = map.getValue(userId.toInt())
                        )
                }
            }
        }
    }

    fun getHashTage(param: String, message: MessageUserDto) {
        //TODO сделать проверку на количество хештегов, пока отображаются все 5
        val dto = atrService.getByName(param)
        message.hashTage = "${dto.attribute1} ${dto.attribute2} ${dto.attribute3} ${dto.attribute4} ${dto.attribute5}"
    }
}