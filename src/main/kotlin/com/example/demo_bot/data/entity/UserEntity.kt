package com.example.demo_bot.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserEntity(

    @Id
    val id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    var messageUserEntity: MessageUserEntity
)