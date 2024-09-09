package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.MessageUserDto

interface MessageUserService {

    fun getMessageByUserId(userId: Int): MessageUserDto

    fun update(id: Int, dto: MessageUserDto)
}