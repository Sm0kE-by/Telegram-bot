package com.example.demo_bot.view.handler.changeData

import com.example.demo_bot.view.model.enums.ChangeDataHandlerName
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument

interface ChangeDataCallbackHandler {


    val name: ChangeDataHandlerName

    fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        argument: String,
    )

}

