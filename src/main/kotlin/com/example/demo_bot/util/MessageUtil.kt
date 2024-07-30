package com.example.demo_bot.util

import com.example.demo_bot.model.BotAttributes
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
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


fun createDialogMenu(chatId: String, text: String, inlineButtons: List<List<Pair<String, String>>>) =
    createMessage(chatId, text).apply {
        replyMarkup = getInlineKeyboard(inlineButtons)
    }

fun getInlineKeyboard(allButtons: List<List<Pair<String, String>>>): InlineKeyboardMarkup =
    InlineKeyboardMarkup().apply {
        keyboard = allButtons.map { rowButtons ->
            rowButtons.map { (data, buttonText) ->
                InlineKeyboardButton().apply {
                    text = buttonText
                    callbackData = data
                }
            }
        }
    }

fun sendMessage(attributes: BotAttributes, listHashTags: List<String>, chatId: String, message: String) =
    createMessage(
        chatId,
        previewMessage(attributes, listHashTags, message)
    )
fun previewMessage(attributes: BotAttributes, listHashTags: List<String>,message: String) =
    """
          [${attributes.attributes.headName}]${attributes.attributes.headLink}
            
            $message      
                                            
            $listHashTags
                                
            ${attributes.attributesLink}
        """.trimIndent()