package com.example.demo_bot.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "message_user")
class MessageUserEntity(
    @Id
    @Column(name = "user_id")
    var userId: Int,
    var title: String = "",
    var text: String = "",
    var link: String = "",
    @Column(name = "hash_tage")
    var hashTage: String = "",
    @Column(name = "social_link")
    var socialLink: String = "",
    @Column(name = "from_handler")
    var fromHandler: String = "",
    @OneToMany(mappedBy = "messageUser", fetch = FetchType.EAGER)
//    @JoinColumn(name = "message_id")
    var listPhoto: List<MessagePhotoEntity> = emptyList()
)