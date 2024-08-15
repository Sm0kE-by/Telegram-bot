package com.example.demo_bot.view.config

import com.example.demo_bot.view.bot.SK_Bot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotConfig {

    @Bean
    fun telegramBotsApi(bot: com.example.demo_bot.view.bot.SK_Bot): TelegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java).apply {
        registerBot(bot)
    }
}