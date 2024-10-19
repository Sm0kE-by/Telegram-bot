package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.SocialMediaLinkEntity
import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.dto.SocialMediaLinkDto
import com.example.demo_bot.service.interfaces.SocialMediaLinkService
import com.example.demo_bot.service.repository.SocialMediaLinkRepository
import jakarta.ws.rs.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SocialMediaLinkServiceImpl(private val socialMediaLinkRepository: SocialMediaLinkRepository) :
    SocialMediaLinkService {

    override fun getAll(): List<SocialMediaLinkDto> = socialMediaLinkRepository.findAllByOrderByName().map { it.toDto() }

    override fun getByName(name: String): SocialMediaLinkDto = socialMediaLinkRepository.findByName(name).toDto()

    override fun getById(id: Int): SocialMediaLinkDto = socialMediaLinkRepository.findByIdOrNull(id)
        ?.toDto()
        ?: throw NotFoundException()

    override fun create(dto: SocialMediaLinkDto) {
        socialMediaLinkRepository.save(dto.toEntity())
    }

    override fun update(id: Int, dto: SocialMediaLinkDto) {
       val socialMediaLinkEntity = socialMediaLinkRepository.findByIdOrNull(id)
           ?: throw NotFoundException()

        socialMediaLinkEntity.name = dto.name
        socialMediaLinkEntity.link = dto.link

        socialMediaLinkRepository.save(socialMediaLinkEntity)
    }

    override fun delete(id: Int) {
       socialMediaLinkRepository.deleteById(id)
    }

    private fun SocialMediaLinkEntity.toDto(): SocialMediaLinkDto = SocialMediaLinkDto(
        id = this.id,
        name = this.name,
        link = this.link
    )
    private fun SocialMediaLinkDto.toEntity(): SocialMediaLinkEntity = SocialMediaLinkEntity(
        id = 0,
        name = this.name,
        link = this.link
    )
}