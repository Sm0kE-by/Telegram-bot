package com.example.demo_bot.util

<<<<<<< HEAD
import com.example.demo_bot.model.ExchangeAttributes
import com.example.demo_bot.model.enums.HandlerName
=======
import com.example.demo_bot.view.model.ExchangeAttributes
import com.example.demo_bot.view.model.enums.HandlerName
>>>>>>> 5cf757e (complite beta version 1.1)



fun getHashTagUtilCreatePost(namePost: String): ArrayList<String> {

    val namePost = namePost
    var list: ArrayList<String> = ArrayList()

    when (namePost) {
        HandlerName.CREATE_POST_ABOUT_CRYPTO.text -> {
            list.add(0, "#Проекты")
            list.add(1, "#Инвестпортфель")
            list.add(2, "#ИнвестПул")
        }

        HandlerName.DAILY_TASKS_IN_GAMES.text -> {
            list.add(0, "#Игры")
            list.add(1, "#Игры_разные2")
            list.add(2, "#Игры3")
        }

        HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> {
            list.add(0, "#Event")
            list.add(1, "#Инвестпортфель")
            list.add(2, "#ИнвестПул")
        }

        HandlerName.INVITE_NEW_GAME.text -> {
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

fun getFromHandlerName(handlerName: String): HandlerName =
    when (handlerName) {
        HandlerName.CREATE_POST_ABOUT_CRYPTO.text -> HandlerName.CREATE_POST_ABOUT_CRYPTO
        HandlerName.DAILY_TASKS_IN_GAMES.text -> HandlerName.DAILY_TASKS_IN_GAMES
        HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE.text -> HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE
        HandlerName.INVITE_NEW_GAME.text -> HandlerName.INVITE_NEW_GAME
        else -> HandlerName.CREATE_POST_MENU
    }

fun getExchangeName(exchangeName: String, exchange: ExchangeAttributes) =
    when (exchangeName) {
        "ByBit" -> "[${exchange.byBit.name}]${exchange.byBit.link}"
        "OKX" -> "[${exchange.okx.name}]${exchange.okx.link}"
        "Mexc" -> "[${exchange.mexc.name}]${exchange.mexc.link}"
        "BingX" -> "[${exchange.bingX.name}]${exchange.bingX.link}"
        else -> ""
    }
