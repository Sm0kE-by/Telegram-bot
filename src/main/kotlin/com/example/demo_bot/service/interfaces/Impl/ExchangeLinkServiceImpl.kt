package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.ExchangeLinkEntity
import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.service.repository.ExchangeLinkRepository
import org.springframework.stereotype.Service


@Service
class ExchangeLinkServiceImpl(private val exchangeLinkRepository: ExchangeLinkRepository) : ExchangeLinkService {

    override fun getAll(): List<ExchangeLinkDto> =
        exchangeLinkRepository.findAllByOrderByName().map { it.toDto() }

    override fun getByName(name: String): ExchangeLinkDto = exchangeLinkRepository.findByName(name).toDto()

    private fun ExchangeLinkEntity.toDto(): ExchangeLinkDto = ExchangeLinkDto(
        id = this.id,
        name = this.name,
        link = this.link
    )
}