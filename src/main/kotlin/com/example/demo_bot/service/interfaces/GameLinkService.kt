package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.GameLinkDto

interface GameLinkService {

    fun getAll(): List<GameLinkDto>

    fun getByName(name: String): GameLinkDto

    fun getById(id: Int): GameLinkDto
}