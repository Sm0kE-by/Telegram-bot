package com.example.demo_bot.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*


@Service
class MessageService(
    @Value("\${locale.tag}")
    private val locale: Locale,
    private val messageSource: MessageSource,
) {



    fun getMessage(message: String): String {
        return messageSource.getMessage(message, null, locale)
    }

    fun getMessage(message: String, vararg args: Any?): String {
        return messageSource.getMessage(message, args, locale)
    }
}