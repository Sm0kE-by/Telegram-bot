package com.example.demo_bot.view.handler.createPost

import com.example.demo_bot.service.dto.GameLinkDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.view.model.enums.CommandName
import com.example.demo_bot.view.model.enums.CreatePostHandlerName
import com.example.demo_bot.util.createTextDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class DailyTaskInGames(
    private val gameLinkService: GameLinkService
) : CreatePostCallbackHandler {

    override val name: CreatePostHandlerName = CreatePostHandlerName.DAILY_TASKS_IN_GAMES
    private val listGameName = gameLinkService.getAll()

    val callbackBack = CommandName.START.text
    val callbackCreateMessage = CreatePostHandlerName.CREATE_MESSAGE.text

    override fun myProcessCallbackData(
        absSender: AbsSender,
        chatId: String,
        message: MessageUserDto,
    ) {
        absSender.execute(
            createTextDialogMenu(
                chatId,
                "Выберите игру",
                getGameName(listGameName),
            )
        )
    }

    private fun getGameName(listGame: List<GameLinkDto>): List<List<Pair<String, String>>> {
        val list = ArrayList<List<Pair<String, String>>>()
        for (i in listGame.indices step 3) {
            if (i + 2 <= listGame.size - 1) {
                list.add(
                    listOf(
                        "$callbackCreateMessage|${listGame[i].name}" to listGame[i].name,
                        "$callbackCreateMessage|${listGame[i + 1].name}" to listGame[i + 1].name,
                        "$callbackCreateMessage|${listGame[i + 2].name}" to listGame[i + 2].name
                    )
                )
            } else if (i + 1 <= listGame.size - 1) {
                list.add(
                    listOf(
                        "$callbackCreateMessage|${listGame[i].name}" to listGame[i].name,
                        "$callbackCreateMessage|${listGame[i + 1].name}" to listGame[i + 1].name
                    )
                )

            } else {
                list.add(
                    listOf(
                        "$callbackCreateMessage|${listGame[i].name}" to listGame[i].name
                    )
                )
            }
        }
        list.add(
            listOf(
                "$callbackBack|create_new_post" to "Назад",
            )
        )
            
        return list
    }
}