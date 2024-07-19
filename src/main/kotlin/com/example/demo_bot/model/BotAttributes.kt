package com.example.demo_bot.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class BotAttributes(
    @Value("\${attributesBot.youtube}")
    youtube:String,
    @Value("\${attributesBot.tiktok}")
    tiktok:String,
    @Value("\${attributesBot.instagram}")
    instagram:String,
    @Value("\${attributesBot.telegraf}")
    telegraf:String,
) {

    var attributes = AttributesModel(youtube, tiktok, instagram, telegraf)

}