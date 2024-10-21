package com.example.demo_bot.view.model


data class MessageUser (
    var userId: Int? = 0,
    var title: String = "",
    var text: String = "",
    var link: String = "",
    var hashTage: String = "",
    var socialLink: String = "",
    var fromHandler: String = "",
    var listPhoto: List<MessagePhoto> = emptyList(),
)