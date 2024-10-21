package com.example.demo_bot.util

import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.dto.SocialMediaLinkDto
import com.example.demo_bot.view.model.MessageUser
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.media.InputMedia
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Ф-ия в которой мы создаём объект SendMessage с указанием текста и chatId.
 * Также включаем поддержку форматирования текста с помощью markdown и запрещаем отображение превью для внешних ссылок,
 * если таковые будут (просто чтобы не загромождать сообщение).
 */
fun createMessage(chatId: String, text: String) =
    SendMessage(chatId, text)
        .apply { enableMarkdown(true) }
        .apply { disableWebPagePreview() }


fun createTextDialogMenu(
    chatId: String,
    text: String,
    inlineButtons: List<List<Pair<String, String>>>,
) =
    createMessage(chatId, text)
        .apply { replyMarkup = getInlineKeyboard(inlineButtons) }

fun getInlineKeyboard(
    allButtons: List<List<Pair<String, String>>>,
    //   fromHandlerName: HandlerName
): InlineKeyboardMarkup =
    InlineKeyboardMarkup().apply {
        keyboard = allButtons.map { rowButtons ->
            rowButtons.map { (data, buttonText) ->
                InlineKeyboardButton().apply {
                    text = buttonText
                    callbackData = "$data"
                    //       "|${fromHandlerName.text}"
                }
            }
        }
    }

fun sendMessage(chatId: String, message: MessageUser) =
    if (message.link.isEmpty()) {
        createMessage(chatId, previewMessage(message))
    } else {
        createMessage(chatId, previewMessageAndLinks(message))
    }

fun createPhotosMessage(absSender: AbsSender, chatId: String, message: MessageUser, inlineButtons: List<List<Pair<String, String>>>,) {
    if (message.listPhoto.size == 1) {
        val photo = InputFile(message.listPhoto[0].telegramFileId)
        val ph = SendPhoto()
        ph.photo = photo
        ph.chatId = chatId
        ph.parseMode = "Markdown"

        if (message.text.isNotEmpty()) ph.caption = message.text
        absSender.execute(ph.apply { replyMarkup = getInlineKeyboard(inlineButtons) })



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
        listPh.chatId = chatId
        listPh.medias = list
        absSender.execute(listPh)
        absSender.execute(SendMessage(chatId,"Отправить данное сообщение?")
            .apply { replyMarkup = getInlineKeyboard(inlineButtons) }
            .apply { enableMarkdown(true) }
            .apply { disableWebPagePreview() })

        //       bot.sendMediaGroup(
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
    }

}


fun previewMessage(
    message: MessageUser
): String {
    return """
          ${message.title}
            
          ${message.text}      
                                            
          ${message.hashTage}    
                                      
          ${message.socialLink}
        """.trimIndent()
}

fun previewMessageAndLinks(
    message: MessageUser
): String {
    return """
          ${message.title}
            
          ${message.text} 
              
          ${message.link}
                                            
          ${message.hashTage}    
                                                
          ${message.socialLink} 
        """.trimIndent()
}

private fun getHashAndSocial(listHashTags: List<String>, socialLink: List<SocialMediaLinkDto>): List<String> {

    var hash = String()
    var social = String()
    listHashTags.forEach { hash += "$it " }
    socialLink.forEach { social += "[${it.name}](${it.link}) " }

    return listOf(hash, social)
}
