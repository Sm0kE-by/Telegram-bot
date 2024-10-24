package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.ExchangeLinkEntity
import com.example.demo_bot.service.dto.ExchangeLinkDto
import com.example.demo_bot.service.interfaces.ExchangeLinkService
import com.example.demo_bot.service.repository.ExchangeLinkRepository
import jakarta.ws.rs.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ExchangeLinkServiceImpl(private val exchangeLinkRepository: ExchangeLinkRepository) : ExchangeLinkService {

    override fun getAll(): List<ExchangeLinkDto> =
        exchangeLinkRepository.findAllByOrderByName().map { it.toDto() }

    override fun getByName(name: String): ExchangeLinkDto = exchangeLinkRepository.findByName(name).toDto()

    override fun getById(id: Int): ExchangeLinkDto = exchangeLinkRepository.findByIdOrNull(id)
        ?.toDto()
        ?: throw NotFoundException()


    override fun create(dto: ExchangeLinkDto) {
        exchangeLinkRepository.save(dto.toEntity())
    }

    @Transactional
    override fun update(id: Int, dto: ExchangeLinkDto) {

        val newExchange = exchangeLinkRepository.findByIdOrNull(id)
            ?: throw NotFoundException()
        newExchange.name = dto.name
        newExchange.link = dto.link
        newExchange.code = dto.code

        exchangeLinkRepository.save(newExchange)
    }

    override fun delete(id: Int) {
        exchangeLinkRepository.deleteById(id)
    }

    private fun ExchangeLinkEntity.toDto(): ExchangeLinkDto = ExchangeLinkDto(
        id = this.id,
        name = this.name,
        link = this.link,
        code = this.code
    )

    private fun ExchangeLinkDto.toEntity(): ExchangeLinkEntity = ExchangeLinkEntity(
        id = 0,
        name = this.name,
        link = this.link,
        code =  this.code
    )
}