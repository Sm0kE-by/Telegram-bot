package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.MessagePhotoEntity
import com.example.demo_bot.data.entity.MessageUserEntity
import com.example.demo_bot.service.dto.MessagePhotoDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.MessageUserService
import com.example.demo_bot.service.repository.MessagePhotoRepository
import com.example.demo_bot.service.repository.MessageUserRepository
import jakarta.ws.rs.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageUserServiceImpl(
    private val messageUserRepository: MessageUserRepository,
    private val messagePhotoRepository: MessagePhotoRepository
) : MessageUserService {

    override fun getMessageByUserId(userId: Int): MessageUserDto = messageUserRepository.findByIdOrNull(userId)
        ?.toDto()
        ?: throw NotFoundException()

    @Transactional
    override fun update(id: Int, dto: MessageUserDto) {
        val existingMessage = messageUserRepository.findByUserId(id)


        existingMessage.title = dto.title
        existingMessage.text = dto.text
        existingMessage.link = dto.link
        existingMessage.hashTage = dto.hashTage
        existingMessage.socialLink = dto.socialLink
        existingMessage.fromHandler = dto.fromHandler
        // existingMessage.listPhoto = dto.listPhoto.map { it.toEntity(existingMessage) }
        messageUserRepository.save(existingMessage)

        val photos = dto.listPhoto.map { it.toEntity(existingMessage) }
        messagePhotoRepository.deleteAllByMessageUser(existingMessage)
       // messagePhotoRepository.saveAll(photos)
        messagePhotoRepository.saveAll(photos)

    }


    private fun MessageUserEntity.toDto(): MessageUserDto = MessageUserDto(

        userId = this.userId,
        title = this.title,
        text = this.text,
        link = this.link,
        hashTage = this.hashTage,
        socialLink = this.socialLink,
        fromHandler = this.fromHandler,
        listPhoto = this.listPhoto.map { it.toDto() },
    )

    private fun MessagePhotoEntity.toDto(): MessagePhotoDto = MessagePhotoDto(
    telegramFileId = this.telegramFileId,
    fileSize = this.fileSize,
    )

    private fun MessagePhotoDto.toEntity(messageUserEntity: MessageUserEntity): MessagePhotoEntity = MessagePhotoEntity(
        id = 0,
        telegramFileId = this.telegramFileId,
        fileSize = this.fileSize,
        messageUser = messageUserEntity
    )
}