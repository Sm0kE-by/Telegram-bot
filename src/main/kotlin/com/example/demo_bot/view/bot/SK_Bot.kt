package com.example.demo_bot.view.bot

import com.example.demo_bot.service.interfaces.*
import com.example.demo_bot.util.createMessage
import com.example.demo_bot.view.handler.changeData.ChangeDataCallbackHandler
import com.example.demo_bot.view.handler.createPost.CreatePostCallbackHandler
import com.example.demo_bot.view.model.ChangeDataModel
import com.example.demo_bot.view.model.MessagePhoto
import com.example.demo_bot.view.model.MessageUser
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
    callbackHandlersCreatePost: Set<CreatePostCallbackHandler>,
    callbackHandlersChangeData: Set<ChangeDataCallbackHandler>,
    @Value("\${telegram.token}")
    token: String,
    commands: ArrayList<BotCommand>,
    private val attributesService: AttributesService,
    private val exchangeLinkService: ExchangeLinkService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val userService: UserService,
) : TelegramLongPollingCommandBot(token) {

    @Value("\${telegram.botName}")
    private val botName: String = ""
    private lateinit var handlerMappingCreatePost: Map<String, CreatePostCallbackHandler>
    private lateinit var handlerMappingChangeData: Map<String, ChangeDataCallbackHandler>
    private val mapChangeData: HashMap<Int, ChangeDataModel> = hashMapOf()
    private val mapMessageUser: HashMap<Int, MessageUser> = hashMapOf()

    init {
        commands.add(BotCommand("/start", "Start"))
        handlerMappingCreatePost = callbackHandlersCreatePost.associateBy { it.name.text }
        handlerMappingChangeData = callbackHandlersChangeData.associateBy { it.name.text }
        this.execute(SetMyCommands(commands, BotCommandScopeDefault(), null))
    }

    override fun getBotUsername(): String = botName

    override fun processNonCommandUpdate(update: Update) {

        if (update.hasMessage()) {

            val userId = update.message.from.id.toInt()

            //Проверка юзера
            if (userId == userService.getByUserById(userId).id) {

                if (!mapMessageUser.containsKey(userId)) {
                    mapMessageUser[userId] = MessageUser()
                }

                if (!mapChangeData.containsKey(userId.toInt())) {
                    mapChangeData[userId] = ChangeDataModel()
                }

              //  val myMessage = messageUserService.getMessageByUserId(userId.toInt())
              //  val myMessage = mapMessageUser[userId.toInt()]
                val chatId = update.message.chatId.toString()

                //Проверка команд
                if (update.message.text == "/start") {
                    mapMessageUser[userId]?.fromHandler = ""
                    handlerMappingCreatePost.getValue(CreatePostHandlerName.START_HANDLER.text)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            mapMessageUser[userId]!!
                        )
                    //Проверка наличия текста в диалоге CREATE_MESSAGE
                } else if (update.message.hasText() && mapMessageUser[userId]?.fromHandler == CreatePostHandlerName.CREATE_MESSAGE.text) {
                    mapMessageUser[userId]?.text = update.message.text

                } else if (update.message.hasText() && mapMessageUser[userId]?.fromHandler == ChangeDataHandlerName.CREATE_DATA_MESSAGE.text) {
                    mapMessageUser[userId]?.text += update.message.text


                } else if (update.message.hasText() && update.message.text.contains("||")) {
                    val listArguments = update.message.text.split("||")
                    //TODO Сделать обработку ошибок

                    when (mapChangeData[userId.toInt()]?.category) {
                        ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> {
                            //TODO Сделать на разное количество атрибутов
                            if (listArguments.size == 5) {
                                mapChangeData[userId]?.attributes?.attribute1 = listArguments[0]
                                mapChangeData[userId]?.attributes?.attribute2 = listArguments[1]
                                mapChangeData[userId]?.attributes?.attribute3 = listArguments[2]
                                mapChangeData[userId]?.attributes?.attribute4 = listArguments[3]
                                mapChangeData[userId]?.attributes?.attribute5 = listArguments[4]
                            }
                        }

                        ChangeDataHandlerName.CHANGE_EXCHANGE.text -> {
                            if (listArguments.size == 3) {
                                mapChangeData[userId]?.exchange?.name = listArguments[0]
                                mapChangeData[userId]?.exchange?.link = listArguments[1]
                            }
                        }

                        ChangeDataHandlerName.CHANGE_GAME.text -> {
                            if (listArguments.size == 3) {
                                mapChangeData[userId]?.game?.name = listArguments[0]
                                mapChangeData[userId]?.game?.link = listArguments[1]
                                mapChangeData[userId]?.game?.clanLink = listArguments[2]
                            } else if (listArguments.size == 2) {
                                mapChangeData[userId]?.game?.name = listArguments[0]
                                mapChangeData[userId]?.game?.link = listArguments[1]
                            }
                        }

                        ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text -> {
                            if (listArguments.size == 2) {
                                mapChangeData[userId]?.socialLink?.name = listArguments[0]
                                mapChangeData[userId]?.socialLink?.link = listArguments[1]
                            }
                        }
                    }


                } else if (update.message.hasText()) {
                    execute(createMessage(chatId, "Вы написали: *${update.message.text}*"))
                    //Если просто фото (надо проверить на наличие заголовка)
                } else if (update.message.hasPhoto() && update.message.mediaGroupId == null) {
                    val listPhotos = listOf(
                        MessagePhoto(
                            telegramFileId = update.message.photo[2].fileId,
                            fileSize = update.message.photo[2].fileSize
                        )
                    )
                    if (update.message.caption != null) mapMessageUser[userId]?.text = update.message.caption!!
                    mapMessageUser[userId]?.listPhoto = listPhotos
                    //                   messageUserService.update(userId.toInt(), myMessage)
                } else if (update.message.mediaGroupId != null) {
                    val listPhoto = ArrayList<MessagePhoto>()
                    listPhoto.addAll(mapMessageUser[userId]!!.listPhoto)
                    listPhoto.add(
                        MessagePhoto(
                            telegramFileId = update.message.photo[2].fileId,
                            fileSize = update.message.photo[2].fileSize
                        )
                    )
                    if (update.message.caption != null) mapMessageUser[userId]?.text = update.message.caption!!
                    mapMessageUser[userId]?.listPhoto = listPhoto
                } else {
                    execute(createMessage(chatId, "Я понимаю только текст!"))
                    execute(SendMediaGroup())
                }
        //        messageUserService.update(userId, mapMessageUser[userId]!!)
            }

        } else if (update.hasCallbackQuery()) {

            val userId = update.callbackQuery.from.id.toInt()

            if (!mapChangeData.containsKey(userId)) {
                mapChangeData.put(userId, ChangeDataModel())
            }
            if (!mapMessageUser.containsKey(userId)) {
                mapMessageUser.put(userId, MessageUser())
            }

            if (userId == userService.getByUserById(userId).id) {
               // var myMessage = messageUserService.getMessageByUserId(userId.toInt())
                val callbackQuery = update.callbackQuery
                val callbackData = callbackQuery.data
                val chatId = callbackQuery.message?.chatId.toString()
                val callbackQueryId = callbackQuery.id
                //???
                execute(AnswerCallbackQuery(callbackQueryId))

                val callbackArguments = callbackData.split("|")
                val callbackHandlerName = callbackArguments.first()

                when (callbackHandlerName) {

                    CreatePostHandlerName.CREATE_POST_MENU.text -> {
                        mapMessageUser[userId] = MessageUser(
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
                            CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text -> mapMessageUser[userId]?.fromHandler =
                                CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text

                            CreatePostHandlerName.INVITE_NEW_GAME.text -> mapMessageUser[userId]?.fromHandler =
                                CreatePostHandlerName.INVITE_NEW_GAME.text

                            CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> mapMessageUser[userId]?.fromHandler =
                                CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text

                            CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> mapMessageUser[userId]?.fromHandler =
                                CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text
                        }
                    }

                    CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text -> {
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text
                        getHashTage(CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text, mapMessageUser[userId]!!)
                    }

                    CreatePostHandlerName.INVITE_NEW_GAME.text -> {
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.INVITE_NEW_GAME.text
                        getHashTage(CreatePostHandlerName.INVITE_NEW_GAME.text, mapMessageUser[userId]!!)
                    }

                    CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> {
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text
                        getHashTage(CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text, mapMessageUser[userId]!!)
                    }

                    CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text
                        getHashTage(CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text, mapMessageUser[userId]!!)
                    }

                    CreatePostHandlerName.CREATE_MESSAGE.text -> {
//                        val fromHandler = HandlerName.CREATE_MESSAGE.text
                        if (callbackArguments[1] != "empty") {
                            when (mapMessageUser[userId]?.fromHandler) {
                                CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> {
                                    val dto = gameLinkService.getByName(callbackArguments[1])
                                    mapMessageUser[userId]?.link = "[${dto.name}]${dto.link} - начни играть прямо сейчас!!!"
                                }

                                CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                                    val dto = exchangeLinkService.getByName(callbackArguments[1])
                                    mapMessageUser[userId]?.link =
                                        "[${dto.name}](${dto.link}) - регистрируйся прямо сейчас и получай крутой бонус по моей реферальной ссылке!!!"
                                }
                            }
                        }
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.CREATE_MESSAGE.text
                    }

                    CreatePostHandlerName.MESSAGE_SKETCH.text -> {
                        mapMessageUser[userId]?.title = "[SKcrypto](https://t.me/DefiSKcrypto)"
                        mapMessageUser[userId]?.socialLink = ""
                        val dto = socialMediaLinkService.getAll()
                        dto.forEach { mapMessageUser[userId]?.socialLink += "[${it.name}](${it.link}) " }

                    }

                    ////////////////////////////////////////////////////////////////////

                    ChangeDataHandlerName.CHANGE_DATA_START_MENU.text -> {
                        mapChangeData[userId] = ChangeDataModel()
                    }

                    ChangeDataHandlerName.CRUD_MENU.text -> {
                        if (callbackArguments.size >= 2) mapChangeData[userId]?.category = callbackArguments[1]
                    }

                    ChangeDataHandlerName.CHANGE_DATA_MESSAGE.text -> {
                        //если нажали CREATE
                        if (callbackArguments.size >= 2 && callbackArguments[1] == ChangeDataHandlerName.CREATE_DATA.text)
                            mapChangeData[userId]?.operation = callbackArguments[1]
                        else {
                            //если нажали UPDATE/DELETE получаю ID выбранного элемента
                            when (mapChangeData[userId]?.category) {
                                ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> {
                                    val data = attributesService.getByName(callbackArguments[1])
                                    mapChangeData[userId]?.attributes?.id = data.id
                                }

                                ChangeDataHandlerName.CHANGE_EXCHANGE.text -> {
                                    val data = exchangeLinkService.getByName(callbackArguments[1])
                                    mapChangeData[userId]?.exchange?.id = data.id
                                }

                                ChangeDataHandlerName.CHANGE_GAME.text -> {
                                    val data = gameLinkService.getByName(callbackArguments[1])
                                    mapChangeData[userId]?.game?.id = data.id
                                }

                                ChangeDataHandlerName.CHANGE_SOCIAL_MEDIA.text -> {
                                    val data = socialMediaLinkService.getByName(callbackArguments[1])
                                    mapChangeData[userId]?.socialLink?.id = data.id
                                }
                            }
                        }
                    }

                    ChangeDataHandlerName.CHANGE_MENU.text -> {
                        //если нажали UPDATE/DELETE
                        if (callbackArguments.size >= 2) mapChangeData[userId]?.operation = callbackArguments[1]
                    }
                }

      //          messageUserService.update(userId, mapMessageUser[userId]!!)

                if (handlerMappingCreatePost.containsKey(callbackHandlerName))
                    handlerMappingCreatePost.getValue(callbackHandlerName)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            mapMessageUser[userId]!!
                        )
                else if (handlerMappingChangeData.containsKey(callbackHandlerName)) {

  //                  if (mapMessageUser[userId]?.fromHandler == ChangeDataHandlerName.CREATE_DATA_MESSAGE.text) {
                        handlerMappingChangeData.getValue(callbackHandlerName)
                            .myProcessCallbackData(
                                absSender = this,
                                chatId = chatId,
                                changeDataModel = mapChangeData.getValue(userId)
                            )
                   // }
                }
            }
        }
    }

    fun getHashTage(param: String, message: MessageUser) {
        //TODO сделать проверку на количество хештегов, пока отображаются все 5
        val dto = attributesService.getByName(param)
        message.hashTage = "${dto.attribute1} ${dto.attribute2} ${dto.attribute3} ${dto.attribute4} ${dto.attribute5}"
    }
}