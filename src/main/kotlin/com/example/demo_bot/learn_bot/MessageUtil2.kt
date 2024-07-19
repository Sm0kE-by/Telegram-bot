package com.example.demo_bot.learn_bot

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

/**
 * Ф-ия в которой мы создаём объект SendMessage с указанием текста и chatId.
 * Также включаем поддержку форматирования текста с помощью markdown и запрещаем отображение превью для внешних ссылок,
 * если таковые будут (просто чтобы не загромождать сообщение).
 */
fun createMessage(chatId: String, text: String) =
    SendMessage(chatId, text)
        .apply { enableMarkdown(true) }
        .apply { disableWebPagePreview() }

/**
 * Внутри используем утилитарный метод createMessageWithSimpleButtons():
 */
fun createMessageWithSimpleButtons(chatId: String, text: String, simpleButtons: List<List<String>>) =
    createMessage(chatId, text)
        .apply {
            replyMarkup = getSimpleKeyboard(simpleButtons)
        }

/**
 * Тут мы просто указываем свойство replyMarkup, для чего вызываем метод getSimpleKeyboard(),
 * передавая ему на вход список списков строк.
 *
 * Внутри создаём объект ReplyKeyboardMarkup. Он отвечает за разметку кнопок.
 * Затем проходимся по каждой строке, создавая KeyboardRow и заполняем её кнопками.
 * Для создания обычной кнопки требуется указать только текст. Флаг oneTimeKeyboard указывает на то,
 * что кнопки исчезнут после однократного нажатия на одну из них. По умолчанию кнопки не исчезают.
 */
fun getSimpleKeyboard(allButtons: List<List<String>>): ReplyKeyboard =
    ReplyKeyboardMarkup().apply {
        keyboard = allButtons.map { rowButtons ->
            val row = KeyboardRow()
            rowButtons.forEach { rowButton -> row.add(rowButton) }
            row
        }
        var com = AnswerCallbackQuery()
        com.callbackQueryId
        CommandName2.BUTTONS
        resizeKeyboard = true
        oneTimeKeyboard = true
    }

/**
 * Утилитарные методы формирования inline-кнопок похожи на уже рассмотренные ранее.
 */
fun createMessageWithInlineButtons(chatId: String, text: String, inlineButtons: List<List<Pair<String, String>>>) =
    createMessage(chatId, text)
        .apply {
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