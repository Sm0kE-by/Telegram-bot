package com.example.demo_bot.util

import com.example.demo_bot.service.dto.MessagePhotoDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.dto.SocialMediaLinkDto
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

/**
 * Ф-ия в которой мы создаём объект SendMessage с указанием текста и chatId.
 * Также включаем поддержку форматирования текста с помощью markdown и запрещаем отображение превью для внешних ссылок,
 * если таковые будут (просто чтобы не загромождать сообщение).
 */
fun createMessage(chatId: String, text: String) =
    SendMessage(chatId, text)
        .apply { enableMarkdown(true) }
        .apply { disableWebPagePreview() }


fun createDialogMenu(
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


fun sendMessage(
    chatId: String,
    message: MessageUserDto
) =
    if (message.link == "") {
        createMessage(chatId, previewMessage(message))
        //       createPhoto(chatId,message.listPhoto)
    } else {
        createMessage(chatId, previewMessageAndLinks(message))
        //       createPhoto(chatId,message.listPhoto)
    }

fun createPhotos(chatId: String, photos: List<MessagePhotoDto>) {
    if (photos.isNotEmpty()) {
        photos.forEach {
            val photo = InputFile(it.telegramFileId)
            SendPhoto(chatId, photo)
        }
    }
}


fun previewMessage(
    message: MessageUserDto
): String {
    return """
          ${message.title}
            
          ${message.text}      
                                            
          ${message.hashTage}    
                                      
          ${message.socialLink}
        """.trimIndent()
}

fun previewMessageAndLinks(
    message: MessageUserDto
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
