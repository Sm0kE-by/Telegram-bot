package com.example.demo_bot.util

import com.example.demo_bot.model.ExchangeAttributes
import com.example.demo_bot.model.enums.HandlerGamesName.KGB
import com.example.demo_bot.model.enums.HandlerName

fun getHashTagUtilCreatePost(namePost: HandlerName): ArrayList<String> {

    val namePost = namePost
    var list: ArrayList<String> = ArrayList()

    when (namePost) {
        HandlerName.MESSAGE_SKETCH -> {
            list.add(0, "#Проекты")
            list.add(1, "#Инвестпортфель")
            list.add(2, "#ИнвестПул")
        }

        HandlerName.DAILY_TASKS_IN_GAMES -> {
            list.add(0, "#Игры")
            list.add(1, "#Игры разные2")
            list.add(2, "#Игры3")
        }

        HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE -> {
            list.add(0, "#Event")
            list.add(1, "#Инвестпортфель")
            list.add(2, "#ИнвестПул")
        }

        HandlerName.INVITE_NEW_GAME -> {
            list.add(0, "#Games")
            list.add(1, "#Игры")
            list.add(2, "#New games")
        }

        else -> {
            list.add(0, "")
        }
    }
    return list
}

fun getFromHandlerName(handlerName: String): HandlerName =
    when (handlerName) {
        HandlerName.CREATE_POST_MENU.text -> HandlerName.CREATE_POST_MENU
        HandlerName.DAILY_TASKS_IN_GAMES.text -> HandlerName.DAILY_TASKS_IN_GAMES
        else -> HandlerName.CREATE_POST_MENU
    }

fun getExchangeName(exchangeName: String, exchange: ExchangeAttributes): String {

    var nameAndLink = ""

    when (exchangeName) {
        "Bybit" -> nameAndLink = "[${exchange.byBit.name}]${exchange.byBit.link}"
        "OKX" -> nameAndLink = "[${exchange.byBit.name}]${exchange.byBit.link}"
        "Mexc" -> nameAndLink = "[${exchange.byBit.name}]${exchange.byBit.link}"
        "BingX" -> nameAndLink = "[${exchange.byBit.name}]${exchange.byBit.link}"
    }
}