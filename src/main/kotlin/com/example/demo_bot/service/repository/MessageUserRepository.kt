package com.example.demo_bot.service.repository

import com.example.demo_bot.data.entity.MessageUserEntity
import org.springframework.data.repository.CrudRepository

interface MessageUserRepository: CrudRepository<MessageUserEntity, Int> {

fun findByUserId(userId: Int): MessageUserEntity

}