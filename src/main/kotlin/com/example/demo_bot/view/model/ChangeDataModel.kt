package com.example.demo_bot.view.model

import com.example.demo_bot.service.dto.AttributesDto
import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.dto.GameLinkDto
import com.example.demo_bot.service.dto.SocialMediaLinkDto

data class ChangeDataModel(
    val dataId: Int? = 0,
    var category: String = "",
    var operation: String = "",
    val attributes: AttributesDto = AttributesDto(
        name = "",
        attribute1 = "",
        attribute2 = "",
        attribute3 = "",
        attribute4 = "",
        attribute5 = ""),
    val exchange: ExchangeLinkDto = ExchangeLinkDto(name = "", link = ""),
    val game: GameLinkDto = GameLinkDto(name = "", link = "", clanLink = ""),
    val socialLink: SocialMediaLinkDto = SocialMediaLinkDto(name = "", link = ""),
)
