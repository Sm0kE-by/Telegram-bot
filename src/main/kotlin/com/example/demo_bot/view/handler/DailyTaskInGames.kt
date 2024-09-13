package com.example.demo_bot.view.handler

import com.example.demo_bot.service.dto.GameLinkDto
import com.example.demo_bot.service.dto.MessageUserDto
import com.example.demo_bot.service.interfaces.GameLinkService
import com.example.demo_bot.view.model.enums.CommandName
import com.example.demo_bot.view.model.GameNameAttributes
import com.example.demo_bot.view.model.enums.HandlerGamesName
import com.example.demo_bot.view.model.enums.HandlerName
import com.example.demo_bot.util.createTextDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class DailyTaskInGames(
    private val gameNameAttributes: GameNameAttributes,
    private val gameLinkService: GameLinkService
) : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.DAILY_TASKS_IN_GAMES
    private val listGameName = gameLinkService.getAll()

    val callbackBack = CommandName.START.text
    val callbackCreateMessage = HandlerName.CREATE_MESSAGE.text
    val fromHandlerName = name

    val callbackKGB = HandlerGamesName.KGB.text
    val callbackPlayCornBattles = HandlerGamesName.PLAY_CORN_BATTLES.text
    val callbackHamsterdam = HandlerGamesName.HAMSTERDAM.text
    val callbackZarGates = HandlerGamesName.ZAR_GATES.text
    val callbackQappiMiner = HandlerGamesName.QAPPI_MINER.text
    val callbackPocketFi = HandlerGamesName.POCKET_FI.text
    val callbackCexIOPowerTap = HandlerGamesName.CEX_IO_POWER_TAP.text
    val callbackSphinx = HandlerGamesName.SPHINX.text
    val callbackBetFury = HandlerGamesName.BET_FURY.text
    val callbackHarvestMoon = HandlerGamesName.HARVEST_MOON.text
    val callbackOkxRacer = HandlerGamesName.OKX_RACER.text
    val callbackRichAnimals = HandlerGamesName.RICH_ANIMALS.text
    val callbackSignie = HandlerGamesName.SIGNIE.text
    val callbackHamsterKombat = HandlerGamesName.HAMSTER_COMBAT.text
    val callbackMtkClickerMafia = HandlerGamesName.MTK_CLICKER_MAFIA.text
    val callbackEarthCoin = HandlerGamesName.EARTH_COIN.text
    val callback1winToken = HandlerGamesName.WIN_TOKEN.text
    val callbackWCoin = HandlerGamesName.W_COIN.text
    val callbackFlareX = HandlerGamesName.FLARE_X.text
    val callbackShuttle = HandlerGamesName.SHUTTLE.text
    val callbackClaytonGame = HandlerGamesName.CLAYTON_GAME.text
    val callbackGatto = HandlerGamesName.GATTO.text
    val callbackTronSpaceApp = HandlerGamesName.TRON_SPACE_APP.text
    val callbackMuskEmpire = HandlerGamesName.MUSK_EMPIRE.text
    val callbackTonMining = HandlerGamesName.TON_MINING.text
    val callbackPixelWallet = HandlerGamesName.PIXEL_WALLET.text
    val callbackCatGoldMiner = HandlerGamesName.CAT_GOLD_MINER.text
    val callbackNotcoin = HandlerGamesName.NOTCOIN.text
    val callbackCityHolderGame = HandlerGamesName.CITY_HOLDER_GAME.text
    val callbackFrogFarm = HandlerGamesName.FROG_FARM.text
    val callbackYescoinWhite = HandlerGamesName.YESCOIN_WHITE.text
    val callbackBrrrrrGame = HandlerGamesName.BRRRRR_GAME.text
    val callbackTimecoinMineYourTime = HandlerGamesName.TIMECOIN_MINE_YOUR_TIME.text
    val callbackDogs = HandlerGamesName.DOGS.text
    val callbackZavodWallet = HandlerGamesName.ZAVOD_WALLET.text
    val callbackPixelTapByPixelverse = HandlerGamesName.PIXEL_TAP_BY_PIXELVERCE.text
    val callbackLimeCoin = HandlerGamesName.LIME_COIN.text
    val callbackTimeFarm = HandlerGamesName.TIME_FARM.text
    val callbackDJDOG = HandlerGamesName.DJ_DOG.text
    val callbackYescoinYellow = HandlerGamesName.YESCOIN_YELLOW.text
    val callbackPretonDrop = HandlerGamesName.PRETON_DROP.text
    val callbackHotWallet = HandlerGamesName.HOT_WALLET.text
    val callbackTonStation = HandlerGamesName.TON_STATION.text
    val callbackTonBitcoinTBTC = HandlerGamesName.TON_BITCOIN_TBTC.text
    val callbackBlum = HandlerGamesName.BLUM.text
    val callbackCatizen = HandlerGamesName.CATIZEN.text
    val callbackBitton = HandlerGamesName.BITTON.text
    val callbackVertus = HandlerGamesName.VERTUS.text
    val callbackSeedApp = HandlerGamesName.SEED_APP.text
    val callbackMemeFiCoin = HandlerGamesName.MEME_FI_COIN.text
    val callbackBcoin2048 = HandlerGamesName.BCOIN_2048.text
    val callbackDuckCoin = HandlerGamesName.DUCK_COIN.text
    val callbackDotcoin = HandlerGamesName.DOT_COIN.text
    val callbackCubes = HandlerGamesName.CUBES.text
    val callbackXBlastApp = HandlerGamesName.X_BLAST_APP.text
    val callbackOgCommunityBot = HandlerGamesName.OG_COMMUNITY_BOT.text
    val callbackTapSwap = HandlerGamesName.TAP_SWAP.text
    val callbackFuelMining = HandlerGamesName.FUEL_MINING.text
    val callbackHoldWallet = HandlerGamesName.HOLD_WALLET.text

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

//                listOf(
//                    listOf(
//                        "$callbackCreateMessage|$callbackKGB" to gameNameAttributes.kgb.name,
//                        "$callbackCreateMessage|$callbackPlayCornBattles" to gameNameAttributes.playCornBattles.name,
//                        "$callbackCreateMessage|$callbackHamsterdam" to gameNameAttributes.hamsterdam.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackZarGates" to gameNameAttributes.zarGates.name,
//                        "$callbackCreateMessage|$callbackQappiMiner" to gameNameAttributes.qappiMiner.name,
//                        "$callbackCreateMessage|$callbackPocketFi" to gameNameAttributes.pocketFi.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackCexIOPowerTap" to gameNameAttributes.cexIOPowerTap.name,
//                        "$callbackCreateMessage|$callbackSphinx" to gameNameAttributes.sphinx.name,
//                        "$callbackCreateMessage|$callbackBetFury" to gameNameAttributes.betFury.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackHarvestMoon" to gameNameAttributes.harvestMoon.name,
//                        "$callbackCreateMessage|$callbackOkxRacer" to gameNameAttributes.okxRacer.name,
//                        "$callbackCreateMessage|$callbackRichAnimals" to gameNameAttributes.richAnimals.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackSignie" to gameNameAttributes.signie.name,
//                        "$callbackCreateMessage|$callbackHamsterKombat" to gameNameAttributes.hamsterKombat.name,
//                        "$callbackCreateMessage|$callbackMtkClickerMafia" to gameNameAttributes.mtkClickerMafia.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackEarthCoin" to gameNameAttributes.earthCoin.name,
//                        "$callbackCreateMessage|$callback1winToken" to gameNameAttributes.winToken.name,
//                        "$callbackCreateMessage|$callbackWCoin" to gameNameAttributes.wCoin.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackFlareX" to gameNameAttributes.flareX.name,
//                        "$callbackCreateMessage|$callbackShuttle" to gameNameAttributes.shuttle.name,
//                        "$callbackCreateMessage|$callbackClaytonGame" to gameNameAttributes.claytonGame.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackGatto" to gameNameAttributes.gatto.name,
//                        "$callbackCreateMessage|$callbackTronSpaceApp" to gameNameAttributes.tronSpaceApp.name,
//                        "$callbackCreateMessage|$callbackMuskEmpire" to gameNameAttributes.muskEmpire.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackTonMining" to gameNameAttributes.tonMining.name,
//                        "$callbackCreateMessage|$callbackPixelWallet" to gameNameAttributes.pixelWallet.name,
//                        "$callbackCreateMessage|$callbackCatGoldMiner" to gameNameAttributes.catGoldMiner.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackNotcoin" to gameNameAttributes.notcoin.name,
//                        "$callbackCreateMessage|$callbackCityHolderGame" to gameNameAttributes.cityHolderGame.name,
//                        "$callbackCreateMessage|$callbackFrogFarm" to gameNameAttributes.frogFarm.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackYescoinWhite" to gameNameAttributes.yescoinWhite.name,
//                        "$callbackCreateMessage|$callbackBrrrrrGame" to gameNameAttributes.brrrrrGame.name,
//                        "$callbackCreateMessage|$callbackTimecoinMineYourTime" to gameNameAttributes.timecoinMineYourTime.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackDogs" to gameNameAttributes.dogs.name,
//                        "$callbackCreateMessage|$callbackZavodWallet" to gameNameAttributes.zavodWallet.name,
//                        "$callbackCreateMessage|$callbackPixelTapByPixelverse" to gameNameAttributes.pixelTapByPixelverse.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackLimeCoin" to gameNameAttributes.limeCoin.name,
//                        "$callbackCreateMessage|$callbackTimeFarm" to gameNameAttributes.timeFarm.name,
//                        "$callbackCreateMessage|$callbackDJDOG" to gameNameAttributes.djDog.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackYescoinYellow" to gameNameAttributes.yescoinYellow.name,
//                        "$callbackCreateMessage|$callbackPretonDrop" to gameNameAttributes.pretonDrop.name,
//                        "$callbackCreateMessage|$callbackHotWallet" to gameNameAttributes.hotWallet.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackTonStation" to gameNameAttributes.tonStation.name,
//                        "$callbackCreateMessage|$callbackTonBitcoinTBTC" to gameNameAttributes.tonBitcoinTBTC.name,
//                        "$callbackCreateMessage|$callbackBlum" to gameNameAttributes.blum.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackCatizen" to gameNameAttributes.catizen.name,
//                        "$callbackCreateMessage|$callbackBitton" to gameNameAttributes.bitton.name,
//                        "$callbackCreateMessage|$callbackVertus" to gameNameAttributes.vertus.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackSeedApp" to gameNameAttributes.seedApp.name,
//                        "$callbackCreateMessage|$callbackMemeFiCoin" to gameNameAttributes.memeFiCoin.name,
//                        "$callbackCreateMessage|$callbackBcoin2048" to gameNameAttributes.bcoin2048.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackDuckCoin" to gameNameAttributes.duckCoin.name,
//                        "$callbackCreateMessage|$callbackDotcoin" to gameNameAttributes.dotcoin.name,
//                        "$callbackCreateMessage|$callbackCubes" to gameNameAttributes.cubes.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackXBlastApp" to gameNameAttributes.xBlastApp.name,
//                        "$callbackCreateMessage|$callbackOgCommunityBot" to gameNameAttributes.ogCommunityBot.name,
//                        "$callbackCreateMessage|$callbackTapSwap" to gameNameAttributes.tapSwap.name,
//                    ),
//                    listOf(
//                        "$callbackCreateMessage|$callbackFuelMining" to gameNameAttributes.fuelMining.name,
//                        "$callbackCreateMessage|$callbackHoldWallet" to gameNameAttributes.holdWallet.name,
//                    ),
//                    listOf(
//                        "$callbackBack|create_new_post" to "Назад",
//                    ),
//
//                    ),
      //          fromHandlerName = name
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