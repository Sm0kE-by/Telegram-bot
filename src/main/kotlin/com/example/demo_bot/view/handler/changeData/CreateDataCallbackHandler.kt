package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.view.model.enums.ChangeDateHandlerName
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreateDataCallbackHandler : ChangeDataCallbackHandler {

    override val name: ChangeDateHandlerName = ChangeDateHandlerName.CREATE_DATA

    override fun myProcessCallbackData(absSender: AbsSender, chatId: String, fromHandler: String) {
        var text = ""
        when (fromHandler) {
            ChangeDateHandlerName.CHANGE_ATTRIBUTES.text -> {
                text = "Вы не можете создать новые HashTags," +
                        " т.к. они создаются для конкретных разделов создания сообщений," +
                        " а создать новый раздел можно только программно! Попробуйте выбрать другую функцию. "
            }

            ChangeDateHandlerName.CHANGE_EXCHANGE.text ->
                text = getSampleText(
                    "название биржи",
                    "реферальную ссылку на аккаунт",
                    "реферальный код"
                )


            ChangeDateHandlerName.CHANGE_GAME.text ->
                text = getSampleText(
                    "название игры",
                    "реферальная ссылка на игру",
                    "реферальная ссылка на Ваш клан (если такой имеется)"
                )

            ChangeDateHandlerName.CHANGE_SOCIAL_MEDIA.text -> text = getSampleText(
                "название соц. сети",
                "ссылка на аккаунт",
                ""
            )
        }
    }

    private fun getSampleText(param1: String, param2: String, param3: String): String =
        "Для создания новой ссылки на игру," +
                " Вам необходимо ввести *$param1*, *$param2* и *$param3*." +
                " Для того чтобы БОТ распознал Ваш текст и занес его в Базу Данных," +
                " его необходимо вводить в специальном формате (Все пункты необходимо разделить спец символом \"||\")" +
                "Пример:\n$param1||$param2||$param3"
}