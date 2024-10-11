package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.SocialMediaLinkEntity
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

    override fun getById(id: Int): SocialMediaLinkDto = socialMediaLinkRepository.findByIdOrNull(id)
        ?.toDto()
        ?: throw NotFoundException()

    private fun SocialMediaLinkEntity.toDto(): SocialMediaLinkDto = SocialMediaLinkDto(
        id = this.id,
        name = this.name,
        link = this.link
    )
}