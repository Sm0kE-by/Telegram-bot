package com.example.demo_bot.service.repository

import com.example.demo_bot.data.entity.MessagePhotoEntity
import com.example.demo_bot.data.entity.MessageUserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface MessagePhotoRepository: CrudRepository<MessagePhotoEntity, Int> {

fun deleteAllByMessageUser(messageUserEntity: MessageUserEntity)
}