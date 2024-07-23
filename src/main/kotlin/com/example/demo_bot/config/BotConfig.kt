package com.example.demo_bot.config

import com.example.demo_bot.bot.SK_Bot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotConfig {

    @Bean
    fun telegramBotsApi(bot: SK_Bot): TelegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java).apply {
        registerBot(bot)
    }
}