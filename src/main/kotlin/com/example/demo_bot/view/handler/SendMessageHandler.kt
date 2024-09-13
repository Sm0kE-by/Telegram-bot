package com.example.demo_bot.view.handler

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.AttributesService
import com.example.demo_bot.service.interfaces.MessagePhotoService
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.view.model.BotAttributes
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.*
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SendMessageHandler(
    private val botAttributes: BotAttributes,
    private val attributesService: AttributesService,
    private val socialMediaLinkService: SocialMediaLinkService,
    private val messagePhotoService: MessagePhotoService
) : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.SEND_MESSAGE

    val callbackBack = HandlerName.START_HANDLER.text
    val socialLink = socialMediaLinkService.getAll()

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto,
    ) {
//        val fromHandlerName = arguments[1]

        //val photo = messagePhotoService.getAllByUserId(1)
        val chatIdSend = "-1002115452577"
 //       val photo123 = InputFile(photo.telegramFileId)
 //       Deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //       val attr = attributesService.getById(1)
 //       val listAttributes: List<String> = listOf(attr.attribute1, attr.attribute2, attr.attribute3, attr.attribute4, attr.attribute5)
//        absSender.execute(SendPhoto("-1002115452577", photo123))
        absSender.execute(
            sendMessage(
                chatIdSend,
                message,
            )
        )
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

    private fun getChatIdForSendMessage(arguments: String): String {

        return when (arguments) {
            HandlerName.CREATE_POST_MENU.text -> "-1002115452577"
            HandlerName.DAILY_TASKS_IN_GAMES.text -> "-1002115452577"
            else -> "-1002115452577"
        }

    }
}
