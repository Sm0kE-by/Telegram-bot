package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.AttributeEntity
import com.example.demo_bot.service.dto.AttributesDto
import com.example.demo_bot.service.repository.AttributesRepository
import com.example.demo_bot.service.interfaces.AttributesService
import org.springframework.stereotype.Service

@Service
class AttributesServiceImpl(
    private val attributesRepository: AttributesRepository
): AttributesService {

    override fun getByName(name: String): AttributesDto  =
        attributesRepository.getByName(name).toDto()


    private fun AttributeEntity.toDto(): AttributesDto =
        AttributesDto(
            id = this.id,
            name = this.name,
            attribute1 = this.attribute1,
            attribute2 = this.attribute2,
            attribute3 = this.attribute3,
            attribute4 = this.attribute4,
            attribute5 = this.attribute5,
        )
}