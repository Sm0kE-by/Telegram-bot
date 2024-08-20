package com.example.demo_bot.view.model

data class MessageModel(
    var title: String,
    var text: String,
    var link: String,
    var hashTags: String,
    var socialLink: String,
    var fromHandler: String,
)