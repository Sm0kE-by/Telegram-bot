package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.ExchangeLinkDto

interface ExchangeLinkService {

    fun getAll(): List<ExchangeLinkDto>
}