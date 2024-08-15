package com.example.demo_bot.service.controllers

import com.example.demo_bot.service.dto.AttributesDto
import com.example.demo_bot.service.interfaces.AttributesService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/attributes")
class AttributesController(
    private val attributesService: AttributesService
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Int): AttributesDto =
        attributesService.getById(id)
}