package com.example.demo_bot.view.bot

import com.example.demo_bot.service.MessageService
import com.example.demo_bot.service.dto.UserDto
import com.example.demo_bot.service.interfaces.*
import com.example.demo_bot.util.createMessage
import com.example.demo_bot.view.handler.changeData.ChangeDataHandler
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
    callbackHandlersChangeData: Set<ChangeDataHandler>,
    @Value("\${telegram.token}")
    token: String,
    commands: ArrayList<BotCommand>,
    private val attributesService: AttributesService,
    private val exchangeLinkService: ExchangeLinkService,
    private val gameLinkService: GameLinkService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val userService: UserService,
    private val messageService: MessageService,
) : TelegramLongPollingCommandBot(token) {

    @Value("\${telegram.botName}")
    private val botName: String = ""
    private lateinit var handlerMappingCreatePost: Map<String, CreatePostCallbackHandler>
    private lateinit var handlerMappingChangeData: Map<String, ChangeDataHandler>
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
            if (userVerification(
                    userId,
                    update.message.from.firstName,
                    update.message.from.lastName,
                    update.message.from.userName
                )
            ) {

                if (!mapMessageUser.containsKey(userId)) {
                    mapMessageUser[userId] = MessageUser()
                }

                if (!mapChangeData.containsKey(userId)) {
                    mapChangeData[userId] = ChangeDataModel()
                }

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

                    when (mapChangeData[userId]?.category) {
                        ChangeDataHandlerName.CHANGE_ATTRIBUTES.text -> {

                            when (listArguments.size) {
                                5 -> {
                                    mapChangeData[userId]?.attributes?.attribute1 = listArguments[0]
                                    mapChangeData[userId]?.attributes?.attribute2 = listArguments[1]
                                    mapChangeData[userId]?.attributes?.attribute3 = listArguments[2]
                                    mapChangeData[userId]?.attributes?.attribute4 = listArguments[3]
                                    mapChangeData[userId]?.attributes?.attribute5 = listArguments[4]
                                }

                                4 -> {
                                    mapChangeData[userId]?.attributes?.attribute1 = listArguments[0]
                                    mapChangeData[userId]?.attributes?.attribute2 = listArguments[1]
                                    mapChangeData[userId]?.attributes?.attribute3 = listArguments[2]
                                    mapChangeData[userId]?.attributes?.attribute4 = listArguments[3]
                                }

                                3 -> {
                                    mapChangeData[userId]?.attributes?.attribute1 = listArguments[0]
                                    mapChangeData[userId]?.attributes?.attribute2 = listArguments[1]
                                    mapChangeData[userId]?.attributes?.attribute3 = listArguments[2]
                                }

                                2 -> {
                                    mapChangeData[userId]?.attributes?.attribute1 = listArguments[0]
                                    mapChangeData[userId]?.attributes?.attribute2 = listArguments[1]
                                }

                                1 -> {
                                    mapChangeData[userId]?.attributes?.attribute1 = listArguments[0]
                                }
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
                    execute(
                        createMessage(
                            chatId,
                            "${messageService.getMessage("sk_bot.youWrote")} <b>${update.message.text}</b>"
                        )
                    )
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
                    execute(createMessage(chatId, messageService.getMessage("sk_bot.onlyUnderstandText")))
                    execute(SendMediaGroup())
                }
            }
        } else if (update.hasCallbackQuery()) {

            val userId = update.callbackQuery.from.id.toInt()

            if (userVerification(
                    userId,
                    update.callbackQuery.from.firstName,
                    update.callbackQuery.from.lastName,
                    update.callbackQuery.from.userName
                )
            ) {
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
                        when (callbackArguments[0]) {
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
                        getHashTage(1, userId)
                    }

                    CreatePostHandlerName.INVITE_NEW_GAME.text -> {
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.INVITE_NEW_GAME.text
                        getHashTage(4, userId)
                    }

                    CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> {
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text
                        getHashTage(2, userId)
                    }

                    CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text
                        getHashTage(3, userId)
                    }

                    CreatePostHandlerName.CREATE_MESSAGE.text -> {
//                        val fromHandler = HandlerName.CREATE_MESSAGE.text
                        if (callbackArguments[1] != "empty") {
                            when (mapMessageUser[userId]?.fromHandler) {
                                CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> {
                                    val dto = gameLinkService.getByName(callbackArguments[1])
                                    mapMessageUser[userId]?.link =
                                        messageService.getMessage(
                                            "sk_bot.startPlayingRightNow.regexp",
                                            dto.link,
                                            dto.name
                                        )
                                }

                                CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
                                    val dto = exchangeLinkService.getByName(callbackArguments[1])
                                    mapMessageUser[userId]?.link =
                                        messageService.getMessage(
                                            "sk_bot.registerOnExchangeRightNow.regexp",
                                            dto.link,
                                            dto.name,
                                            dto.code
                                        )
                                    //                                    "<a href=\"${dto.link}\">${dto.name}</a> - регистрируйся прямо сейчас, или введи мой реферальный код <b>${dto.code}</b>, и получай крутой бонус по моей реферальной ссылке!!!"
                                }
                            }
                        }
                        mapMessageUser[userId]?.fromHandler = CreatePostHandlerName.CREATE_MESSAGE.text
                    }

                    CreatePostHandlerName.MESSAGE_SKETCH.text -> {
                        mapMessageUser[userId]?.title = messageService.getMessage("sk_bot.titleGroup")
                        mapMessageUser[userId]?.socialLink = ""
                        val dto = socialMediaLinkService.getAll()
                        dto.forEach {
                            mapMessageUser[userId]?.socialLink += "${
                                messageService.getMessage(
                                    "sk_bot.socialLink.regexp",
                                    it.link,
                                    it.name
                                )
                            } "
                            //                       "<a href=\"${it.link}\">${it.name}</a> " }
                        }
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

                if (handlerMappingCreatePost.containsKey(callbackHandlerName))
                    handlerMappingCreatePost.getValue(callbackHandlerName)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            mapMessageUser[userId]!!
                        )
                else if (handlerMappingChangeData.containsKey(callbackHandlerName)) {
                    handlerMappingChangeData.getValue(callbackHandlerName)
                        .myProcessCallbackData(
                            absSender = this,
                            chatId = chatId,
                            changeDataModel = mapChangeData.getValue(userId)
                        )
                }
            }
        }
    }

    fun getHashTage(id: Int, userId: Int) {
        val dto = attributesService.getById(id = id)
        if (dto.attribute1 != "") mapMessageUser[userId]?.hashTage += "#${dto.attribute1}  "
        if (dto.attribute2 != "") mapMessageUser[userId]?.hashTage += "#${dto.attribute2}  "
        if (dto.attribute3 != "") mapMessageUser[userId]?.hashTage += "#${dto.attribute3}  "
        if (dto.attribute4 != "") mapMessageUser[userId]?.hashTage += "#${dto.attribute4}  "
        if (dto.attribute5 != "") mapMessageUser[userId]?.hashTage += "#${dto.attribute5}  "
    }

    private fun userVerification(
        userId: Int,
        firstName: String,
        lastName: String,
        userName: String
    ): Boolean {
        var result = false
        if (mapMessageUser.containsKey(userId) && mapChangeData.containsKey(userId)) result = true
        else if (userService.searchByUserById(userId)) result = true
        else if (userName == "KIBAB177" || userName == "Smoke_by") {
            val userDto = UserDto(
                id = userId,
                firstName = firstName,
                lastName = lastName,
                userName = userName
            )
            userService.create(userDto)
            mapMessageUser[userId] = MessageUser()
            mapChangeData[userId] = ChangeDataModel()
            result = true
        }
        return result
    }
}