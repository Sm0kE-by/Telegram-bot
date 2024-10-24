package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.AttributeEntity
import com.example.demo_bot.service.dto.AttributesDto
import com.example.demo_bot.service.repository.AttributesRepository
import com.example.demo_bot.service.interfaces.AttributesService
import jakarta.ws.rs.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AttributesServiceImpl(
    private val attributesRepository: AttributesRepository
) : AttributesService {

    override fun getAll(): List<AttributesDto> = attributesRepository.findAllByOrderByName().map { it.toDto() }

    override fun getByName(name: String): AttributesDto =
        attributesRepository.findByName(name).toDto()

    override fun getById(id: Int): AttributesDto =
        attributesRepository.findByIdOrNull(id)
            ?.toDto()
            ?: throw NotFoundException("No find Attribute with id: $id")


    override fun update(id: Int, dto: AttributesDto) {
        val attributes = attributesRepository.findByIdOrNull(id)
            ?: throw NotFoundException()
        attributes.attribute1 = dto.attribute1
        attributes.attribute2 = dto.attribute2
        attributes.attribute3 = dto.attribute3
        attributes.attribute4 = dto.attribute4
        attributes.attribute5 = dto.attribute5

        attributesRepository.save(attributes)
    }

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

    private fun AttributesDto.toEntity(): AttributeEntity =
        AttributeEntity(
            id = 0,
            name = this.name,
            attribute1 = this.attribute1,
            attribute2 = this.attribute2,
            attribute3 = this.attribute3,
            attribute4 = this.attribute4,
            attribute5 = this.attribute5,
        )
}