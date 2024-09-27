package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.AttributesService
import com.example.demo_bot.service.interfaces.MessagePhotoService
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.view.model.BotAttributes
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.media.InputMedia
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SendMessageHandler(
    private val botAttributes: BotAttributes,
    private val attributesService: AttributesService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val messagePhotoService: MessagePhotoService
) : CreatePostCallbackHandler {

    override val name: CreatePostHandlerName = CreatePostHandlerName.SEND_MESSAGE

    val callbackBack = CreatePostHandlerName.START_HANDLER.text
    val socialLink = socialMediaLinkService.getAll()

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto,
    ) {
        val chatIdSend = "-1002115452577"

        if (message.text.isNotEmpty() && message.listPhoto.isEmpty()) {
            absSender.execute(
                sendMessage(
                    chatIdSend,
                    message,
                )
            )
        } else if (message.listPhoto.isNotEmpty()) {
            if (message.listPhoto.size == 1) {
                val photo = InputFile(message.listPhoto[0].telegramFileId)
                val ph = SendPhoto()
                ph.photo = photo
                ph.chatId = chatIdSend
                ph.parseMode = "Markdown"
                if (message.text.isNotEmpty()) ph.caption = message.text
            } else if (message.listPhoto.size in 2..10) {

                val list = ArrayList<InputMedia>()
                for (i in 0..<message.listPhoto.size) {
                    if (message.text.isNotEmpty() && i == 0) {
                        val photo = InputMediaPhoto()
                        photo.media = message.listPhoto[i].telegramFileId
                        photo.caption = message.text
                        photo.parseMode = "Markdown"
                        list.add(
                            photo
                        )
                    } else list.add(InputMediaPhoto(message.listPhoto[i].telegramFileId))
                }
                val listPh = SendMediaGroup()
                listPh.chatId = chatIdSend
                listPh.medias = list
                absSender.execute(listPh)
            }
            absSender.execute(
                createTextDialogMenu(
                    chatId,
                    "Сообщение отправлено",
                    listOf(
                        listOf("$callbackBack|back" to "Готово"),
                    ),
//                fromHandlerName = getFromHandlerName(fromHandlerName)
                )
            )
        }
    }

    private fun getChatIdForSendMessage(arguments: String): String {

        return when (arguments) {
            CreatePostHandlerName.CREATE_POST_MENU.text -> "-1002115452577"
            CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> "-1002115452577"
            else -> "-1002115452577"
        }

    }
}
