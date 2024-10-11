package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.GamesLinkEntity
import com.example.demo_bot.service.dto.GameLinkDto
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.service.repository.GamesLinkRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.ws.rs.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GameLinkServiceImpl(private val gamesLinkRepository: GamesLinkRepository) : GameLinkService {

    override fun getAll(): List<GameLinkDto> = gamesLinkRepository.findAllByOrderByName().map { it.toDto() }

    override fun getByName(name: String): GameLinkDto = gamesLinkRepository.getByName(name).toDto()

    override fun getById(id: Int): GameLinkDto = gamesLinkRepository.findByIdOrNull(id)
        ?.toDto()
        ?: throw NotFoundException()

    private fun GamesLinkEntity.toDto(): GameLinkDto = GameLinkDto(
        id = this.id,
        name = this.name,
        link = this.link,
        clanLink = this.clanLink
    )
}