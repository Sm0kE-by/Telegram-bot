package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.MessagePhotoEntity
import com.example.demo_bot.data.entity.MessageUserEntity
import com.example.demo_bot.data.entity.UserEntity
import com.example.demo_bot.service.dto.MessagePhotoDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.dto.UserDto
import com.example.demo_bot.service.interfaces.UserService
import com.example.demo_bot.service.repository.UserRepository
import jakarta.ws.rs.NotFoundException
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getByUserById(id: Int): UserDto = userRepository.findByIdOrNull(id)
        ?.toDto()
        ?: throw NotFoundException("User with id: $id")

    override fun create(user: UserDto): UserDto {
        TODO("Not yet implemented")
    }

    private fun UserEntity.toDto(): UserDto = UserDto(

        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        userName = this.userName,
        messageUserDto = this.messageUserEntity.toDto(),
    )

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
}