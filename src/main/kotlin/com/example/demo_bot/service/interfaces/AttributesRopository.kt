package com.example.demo_bot.service.interfaces

import com.example.demo_bot.data.entity.AttributeEntity
import com.example.demo_bot.service.dto.AttributesDto
import org.springframework.data.repository.CrudRepository

interface AttributesRepository: CrudRepository<AttributeEntity, Int> {

    fun getById(id:Int): AttributeEntity
}