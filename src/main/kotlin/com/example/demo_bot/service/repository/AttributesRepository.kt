package com.example.demo_bot.service.repository

import com.example.demo_bot.data.entity.AttributeEntity
import org.springframework.data.repository.CrudRepository

interface AttributesRepository: CrudRepository<AttributeEntity, Int> {

    fun findAllByOrderByName(): List<AttributeEntity>

    fun findByName(name: String): AttributeEntity
}