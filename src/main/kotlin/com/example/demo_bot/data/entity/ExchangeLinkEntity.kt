package com.example.demo_bot.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "exchange_links")
class ExchangeLinkEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String = "",
    val link: String = ""
)