package com.example.demo_bot.service.dto

import com.example.demo_bot.data.entity.MessageUserEntity
import jakarta.persistence.*

data class UserDto(

    val id: Int? = null,
    var firstName: String,
    var lastName: String,
    var userName: String,
    var messageUserDto: MessageUserDto
)
