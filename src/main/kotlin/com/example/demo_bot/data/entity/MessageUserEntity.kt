package com.example.demo_bot.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "message_user")
class MessageUserEntity(
    @Id
    @Column(name = "user_id")
    var userId: Int,
    var title: String = "",
    var text: String = "",
    var link: String = "",
    var hashTags: String = "",
    var socialLink: String = "",
    var fromHandler: String = "",
)