package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.AttributesDto

interface AttributesService {

    fun getById(id: Int): AttributesDto
}