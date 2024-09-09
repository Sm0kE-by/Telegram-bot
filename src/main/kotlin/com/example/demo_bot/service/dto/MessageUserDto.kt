package com.example.demo_bot.service.dto

import jakarta.persistence.Column

data class MessageUserDto(
    var userId: Int? = null,
    var title: String,
    var text: String,
    var link: String,
    var hashTage: String,
    var socialLink: String,
    var fromHandler: String,
    var listPhoto: List<MessagePhotoDto>,
)
