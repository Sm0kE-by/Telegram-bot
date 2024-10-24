package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.UserDto

interface UserService {

    fun getByUserById(id: Int): UserDto

    fun searchByUserById(id: Int): Boolean

    fun create(user: UserDto)
}