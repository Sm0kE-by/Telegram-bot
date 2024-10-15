package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.SocialMediaLinkDto

interface SocialMediaLinkService {

    fun getAll(): List<SocialMediaLinkDto>

    fun getById(id: Int): SocialMediaLinkDto

    fun create(dto: SocialMediaLinkDto)

    fun update(id: Int, dto: SocialMediaLinkDto)

    fun delete(id: Int)
}