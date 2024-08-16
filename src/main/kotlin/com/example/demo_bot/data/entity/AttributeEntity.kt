package com.example.demo_bot.data.entity

import jakarta.persistence.*


@Entity
@Table(name = "attributes")
class AttributeEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @Column(name = "first_name")
    val typeName: String = "",
    var attribute1: String = "",
    var attribute2: String = "",
    var attribute3: String = "",
    var attribute4: String = "",
    var attribute5: String = "",
)