package com.example.demo_bot.service.repository

import com.example.demo_bot.data.entity.GamesLinkEntity
import org.springframework.data.repository.CrudRepository

interface GamesLinkRepository: CrudRepository<GamesLinkEntity, Int> {

    fun findAllByOrderByName(): List<GamesLinkEntity>

    fun getByName(name: String): GamesLinkEntity
}