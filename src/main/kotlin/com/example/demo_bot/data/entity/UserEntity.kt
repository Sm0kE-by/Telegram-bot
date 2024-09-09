package com.example.demo_bot.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "tg_user")
class UserEntity(

    @Id
    val id: Int = 0,
    @Column(name = "first_name")
    var firstName: String = "",
    @Column(name = "last_name")
    var lastName: String = "",
    @Column(name = "user_name")
    var userName: String = "",
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    var messageUserEntity: MessageUserEntity
)