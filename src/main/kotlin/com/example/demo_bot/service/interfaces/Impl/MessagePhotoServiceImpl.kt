package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.MessagePhotoEntity
import com.example.demo_bot.service.dto.MessagePhotoDto
import com.example.demo_bot.service.interfaces.MessagePhotoService
import com.example.demo_bot.service.repository.MessagePhotoRepository
import jakarta.ws.rs.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MessagePhotoServiceImpl(private val messagePhotoRepository: MessagePhotoRepository) : MessagePhotoService {
//    override fun getAllByUserId(userId: Int): List<MessagePhotoDto> = messagePhotoRepository.findBy(userId)
//        ?.toDto()
//        ?: throw NotFoundException()
//
//    override fun save(userId: Int, listDto: List<MessagePhotoDto>) {
//
//        val existingMessage = messagePhotoRepository.findByIdOrNull(userId)
//            ?: throw NotFoundException ("User not found")
//
//        existingMessage.telegramFileId = listDto.telegramFileId
//        existingMessage.fileSize = listDto.fileSize
//
//        messagePhotoRepository.save(existingMessage)
//    }
//
//    private fun MessagePhotoEntity.toDto(): MessagePhotoDto = MessagePhotoDto(
//
//        id = this.id,
//        telegramFileId = this.telegramFileId,
//        fileSize = this.fileSize,
//        )
}