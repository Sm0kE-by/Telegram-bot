package com.example.demo_bot.util

fun getSampleDataText(param1: String, param2: String, param3: String): String =
    "Для создания новой ссылки на игру," +
            " Вам необходимо ввести *$param1*, *$param2* и *$param3*." +
            " Для того чтобы БОТ распознал Ваш текст и занес его в Базу Данных," +
            " его необходимо вводить в специальном формате (Все пункты необходимо разделить спец символом \"||\")" +
            "\n*Пример*:\n$param1||$param2||$param3"