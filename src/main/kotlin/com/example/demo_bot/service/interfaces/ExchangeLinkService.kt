package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.ExchangeLinkDto

interface ExchangeLinkService {

    fun getAll(): List<ExchangeLinkDto>

    fun getByName(name: String): ExchangeLinkDto

    fun getById(id: Int): ExchangeLinkDto

    fun create(dto: ExchangeLinkDto)

    fun update(id: Int, dto: ExchangeLinkDto)

    fun delete(id: Int)
}