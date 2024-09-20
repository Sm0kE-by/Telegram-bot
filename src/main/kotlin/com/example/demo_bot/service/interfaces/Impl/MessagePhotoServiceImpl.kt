package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.MessagePhotoEntity
import com.example.demo_bot.service.dto.MessagePhotoDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.MessagePhotoService
import com.example.demo_bot.service.repository.MessagePhotoRepository
import jakarta.ws.rs.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MessagePhotoServiceImpl(private val messagePhotoRepository: MessagePhotoRepository) : MessagePhotoService {

//    override fun getAllByMessageUser(messageUserDto: MessageUserDto): List<MessagePhotoDto> =
//     messagePhotoRepository.findAllByMessageUser(messageUserDto)
//        ?.toDto()
//        ?: throw NotFoundException()

//    override fun save(dto: MessagePhotoDto) {
//        messagePhotoRepository.save(dto)
//        //        val existingMessage = messagePhotoRepository.findByIdOrNull(userId)
////            ?: throw NotFoundException ("User not found")
////
////        existingMessage.telegramFileId = listDto.telegramFileId
////        existingMessage.fileSize = listDto.fileSize
////
////        messagePhotoRepository.save(existingMessage)
////    }
////
//        private fun MessagePhotoDto.toDto(): MessagePhotoEntity = MessagePhotoEntity(
//            id = 0,
//            telegramFileId = this.telegramFileId,
//            fileSize = this.fileSize,
//            messageUser = this.
//        )


    }