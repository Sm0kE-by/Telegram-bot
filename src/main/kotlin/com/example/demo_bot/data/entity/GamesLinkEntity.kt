package com.example.demo_bot.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "games_links")
class GamesLinkEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    var name: String = "",
    var link: String = "",
    @Column(name = "clan_link")
    var clanLink: String = ""
)