package com.example.demo_bot.service.interfaces

import com.example.demo_bot.service.dto.AttributesDto

interface AttributesService {

    fun getAll(): List<AttributesDto>

    fun getByName(name: String): AttributesDto

    fun getById(id: Int): AttributesDto

 //   fun create(dto: AttributesDto)

    fun update(id: Int, dto: AttributesDto)

//    fun delete(id: Int)

}