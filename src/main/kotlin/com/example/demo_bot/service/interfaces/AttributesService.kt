package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.AttributesDto

interface AttributesService {

    fun getByName(name: String): AttributesDto

    fun getById(id: Int): AttributesDto

}