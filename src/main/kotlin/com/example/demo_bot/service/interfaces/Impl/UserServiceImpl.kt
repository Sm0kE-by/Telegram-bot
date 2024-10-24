package com.example.demo_bot.service.interfaces.Impl

import com.example.demo_bot.data.entity.UserEntity
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
       ?: throw NotFoundException("No find User with id: $id")

    override fun searchByUserById(id: Int): Boolean  = userRepository.existsById(id)

    override fun create(user: UserDto) {
        userRepository.save(user.toEntity())
    }

    private fun UserEntity.toDto(): UserDto = UserDto(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        userName = this.userName,
    )
    private fun UserDto.toEntity(): UserEntity = UserEntity(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        userName = this.userName,
    )
}