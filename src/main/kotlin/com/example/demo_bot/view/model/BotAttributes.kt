package com.example.demo_bot.view.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class BotAttributes(

    @Value("\${head_message.name}")
    headName:String,
    @Value("\${head_message.link}")
    headLink:String,

    @Value("\${attributesBot.youtube_attribute}")
    youtubeAttributes: String,
    @Value("\${attributesBot.tiktok_attribute}")
    tiktokAttributes: String,
    @Value("\${attributesBot.instagram_attribute}")
    instagramAttributes: String,
    @Value("\${attributesBot.telegraph_attribute}")
    telegraphAttributes: String,

    @Value("\${attributesBot.youtube}")
    youtube: String,
    @Value("\${attributesBot.tiktok}")
    tiktok: String,
    @Value("\${attributesBot.instagram}")
    instagram: String,

    @Value("\${attributesBot.telegraph}")
    telegraph: String,
) {

    var attributes = AttributesModel(
        headName = headName,
        headLink = headLink,

        youtubeAttributes = youtubeAttributes,
        tiktokAttributes = tiktokAttributes,
        instagramAttributes = instagramAttributes,
        telegraphAttributes = telegraphAttributes,

        youtube = youtube,
        tiktok = tiktok,
        instagram = instagram,
        telegraph = telegraph)

    val attributesLink =
        "[$youtubeAttributes]$youtube | [$tiktokAttributes]$tiktok | [$instagramAttributes]$instagram | [$telegraphAttributes]$telegraph"

}