package com.example.demo_bot.service.repository

import com.example.demo_bot.data.entity.SocialMediaLinkEntity
import org.springframework.data.repository.CrudRepository

interface SocialMediaLinkRepository: CrudRepository<SocialMediaLinkEntity, Int> {

    fun findAllByOrderByName(): List<SocialMediaLinkEntity>
}