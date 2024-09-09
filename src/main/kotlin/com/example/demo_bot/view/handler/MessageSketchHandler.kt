package com.example.demo_bot.view.handler

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.view.learn_bot.createMessage
import com.example.demo_bot.view.model.BotAttributes
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.ForwardMessage
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.media.InputMedia
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class MessageSketchHandler(
    private val botAttributes: BotAttributes,
    private val socialMediaLinkService: SocialMediaLinkService
) : MyCallbackHandlerBot {


    override val name: HandlerName = HandlerName.MESSAGE_SKETCH

    //    lateinit var list: List<String>
    lateinit var attributesLink: String

    val callbackSend = HandlerName.SEND_MESSAGE.text
    val callbackBack = HandlerName.CREATE_MESSAGE.text
    private val socialLink = socialMediaLinkService.getAll()

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto,
    ) {//val messageUser = callbackQuery.message.text.toString()

//        //???????????????
//        absSender.execute(
//            EditMessageReplyMarkup(
//                chatId,
//                callbackQuery.message.messageId,
//                callbackQuery.inlineMessageId,
//                getInlineKeyboard(emptyList())
//            )
//        )

        if (message.text.isEmpty() && message.listPhoto.isEmpty()) {
            absSender.execute(createMessage(chatId, "Вы не ввели сообщение!!!"))
//            absSender.execute(
//                createDialogMenu(
//                    chatId,
//                    "Введите текс сообщения",
//                    listOf(
//                        listOf("$callbackNext|next" to "Далее"),
//                        listOf("$callbackBack|back" to "Назад"),
//                    ),
//                    fromHandlerName = name
//                )
//            )
        } else {
//            list = getHashTagUtilCreatePost(fromHandlerName)
//            attributesLink = botAttributes.attributesLink

            absSender.execute(
                createDialogMenu(
                    chatId = chatId,
                    text = getTextMessage(message),
                    inlineButtons = listOf(
                        listOf("$callbackSend|send" to "Отправить пост"),
                        listOf("$callbackBack|back" to "Назад"),
                    ),
//                    fromHandlerName = getFromHandlerName(fromHandlerName)
                )
            )
            if (message.listPhoto.isNotEmpty()) {

                val list: List<InputMedia> = listOf(
                    InputMediaPhoto(message.listPhoto[0].telegramFileId),
                   // message.text
                )

                absSender.execute(SendMediaGroup(
                    chatId, list))
                absSender.execute(ForwardMessage())

//                bot.sendMediaGroup(
//                    chatId = ChatId.fromId(message.chat.id),
//                    mediaGroup = MediaGroup.from(
//                        InputMediaPhoto(
//                            media = ByUrl("https://www.sngular.com/wp-content/uploads/2019/11/Kotlin-Blog-1400x411.png"),
//                            caption = "I come from an url :P",
//                        ),
//                        InputMediaPhoto(
//                            media = ByUrl("https://www.sngular.com/wp-content/uploads/2019/11/Kotlin-Blog-1400x411.png"),
//                            caption = "Me too!",
//                        ),
//                    ),
//                    replyToMessageId = message.messageId,
//                )
//
//                message.listPhoto.forEach {
//                    val photo = InputFile(it.telegramFileId)
//                    absSender.execute(SendPhoto(chatId, photo))
//                }
            }
        }
    }

//    companion object {
//        const val createNewPost = "create_new_post"
//        const val inviteNewGame = "invite_new_game"
//        const val newEventOnCryptoExchange = "new_event_on_crypto"
//        const val dailyTaskInGames = "daily_task_in_games"
//    }

    private fun getTextMessage(message: MessageUserDto) =
        if (message.link == "") {
            previewMessage(message)
        } else {
            previewMessageAndLinks(message)
        }


}