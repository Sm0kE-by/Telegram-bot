package com.example.demo_bot.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "games_links")
class GamesLinkEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String = "",
    val link: String = "",
    @Column(name = "clan_link")
    val clanLink: String = ""
)