package com.example.demo_bot.util

import com.example.demo_bot.view.model.ExchangeAttributes
import com.example.demo_bot.view.model.enums.CreatePostHandlerName


fun getHashTagUtilCreatePost(namePost: String): ArrayList<String> {

    val namePost = namePost
    var list: ArrayList<String> = ArrayList()

    when (namePost) {
        CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text -> {
            list.add(0, "#Проекты")
            list.add(1, "#Инвестпортфель")
            list.add(2, "#ИнвестПул")
        }

        CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> {
            list.add(0, "#Игры")
            list.add(1, "#Игры_разные2")
            list.add(2, "#Игры3")
        }

        CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
            list.add(0, "#Event")
            list.add(1, "#Инвестпортфель")
            list.add(2, "#ИнвестПул")
        }

        CreatePostHandlerName.INVITE_NEW_GAME.text -> {
            list.add(0, "#Games")
            list.add(1, "#Игры")
            list.add(2, "#New_games")
        }

        else -> {
            list.add(0, "неверное значение")
        }
    }
    return list
}

fun getFromHandlerName(handlerName: String): CreatePostHandlerName =
    when (handlerName) {
        CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO.text -> CreatePostHandlerName.CREATE_POST_ABOUT_CRYPTO
        CreatePostHandlerName.DAILY_TASKS_IN_GAMES.text -> CreatePostHandlerName.DAILY_TASKS_IN_GAMES
        CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> CreatePostHandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE
        CreatePostHandlerName.INVITE_NEW_GAME.text -> CreatePostHandlerName.INVITE_NEW_GAME
        else -> CreatePostHandlerName.CREATE_POST_MENU
    }

fun getExchangeName(exchangeName: String, exchange: ExchangeAttributes) =
    when (exchangeName) {
        "ByBit" -> "[${exchange.byBit.name}]${exchange.byBit.link}"
        "OKX" -> "[${exchange.okx.name}]${exchange.okx.link}"
        "Mexc" -> "[${exchange.mexc.name}]${exchange.mexc.link}"
        "BingX" -> "[${exchange.bingX.name}]${exchange.bingX.link}"
        else -> ""
    }
