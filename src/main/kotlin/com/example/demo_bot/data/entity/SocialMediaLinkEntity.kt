package com.example.demo_bot.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "social_media_links")
class SocialMediaLinkEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    var name: String = "",
    var link: String = ""
)