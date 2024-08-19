package com.example.demo_bot.service.repository

import com.example.demo_bot.data.entity.ExchangeLinkEntity
import org.springframework.data.repository.CrudRepository

interface ExchangeLinkRepository: CrudRepository<ExchangeLinkEntity, Int> {

    fun findAllByOrderByName(): List<ExchangeLinkEntity>

    fun findByName(name: String): ExchangeLinkEntity
}