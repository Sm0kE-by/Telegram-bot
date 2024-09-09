package com.example.demo_bot.service.repository

import com.example.demo_bot.data.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, Int> {


}