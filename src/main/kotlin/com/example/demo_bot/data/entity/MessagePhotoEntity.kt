package com.example.demo_bot.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "message_photo")
class MessagePhotoEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int =0,
    @Column(name = "telegram_file_id")
    var telegramFileId: String = "",
    @Column(name = "file_size")
    var fileSize: Int = 0,
    @ManyToOne
    @JoinColumn(name = "message_id")
    var messageUser: MessageUserEntity,
)