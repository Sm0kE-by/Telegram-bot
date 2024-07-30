package com.example.demo_bot.handler

import com.example.demo_bot.model.CommandName
import com.example.demo_bot.model.GameNameAttributes
import com.example.demo_bot.model.HandlerGamesName
import com.example.demo_bot.model.HandlerName
import com.example.demo_bot.util.createDialogMenu
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class DailyTaskInGames(private val gameNameAttributes: GameNameAttributes) : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.DAILY_TASKS_IN_GAMES

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
        callbackQuery: CallbackQuery,
        arguments: List<String>,
        message: String
    ) {

        val chatId = callbackQuery.message.chatId.toString()

        absSender.execute(
            createDialogMenu(
                chatId,
                "Выберите игру",
                listOf(
                    listOf(
                        "$callbackCreateMessage|$callbackKGB|$fromHandlerName" to gameNameAttributes.kgb.name,
                        "$callbackCreateMessage|$callbackPlayCornBattles|$fromHandlerName" to gameNameAttributes.playCornBattles.name,
                        "$callbackCreateMessage|$callbackHamsterdam|$fromHandlerName" to gameNameAttributes.hamsterdam.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackZarGates|$fromHandlerName" to gameNameAttributes.zarGates.name,
                        "$callbackCreateMessage|$callbackQappiMiner|$fromHandlerName" to gameNameAttributes.qappiMiner.name,
                        "$callbackCreateMessage|$callbackPocketFi|$fromHandlerName" to gameNameAttributes.pocketFi.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackCexIOPowerTap|$fromHandlerName" to gameNameAttributes.cexIOPowerTap.name,
                        "$callbackCreateMessage|$callbackSphinx|$fromHandlerName" to gameNameAttributes.sphinx.name,
                        "$callbackCreateMessage|$callbackBetFury|$fromHandlerName" to gameNameAttributes.betFury.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackHarvestMoon|$fromHandlerName" to gameNameAttributes.harvestMoon.name,
                        "$callbackCreateMessage|$callbackOkxRacer|$fromHandlerName" to gameNameAttributes.okxRacer.name,
                        "$callbackCreateMessage|$callbackRichAnimals|$fromHandlerName" to gameNameAttributes.richAnimals.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackSignie|$fromHandlerName" to gameNameAttributes.signie.name,
                        "$callbackCreateMessage|$callbackHamsterKombat|$fromHandlerName" to gameNameAttributes.hamsterKombat.name,
                        "$callbackCreateMessage|$callbackMtkClickerMafia|$fromHandlerName" to gameNameAttributes.mtkClickerMafia.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackEarthCoin|$fromHandlerName" to gameNameAttributes.earthCoin.name,
                        "$callbackCreateMessage|$callback1winToken|$fromHandlerName" to gameNameAttributes.winToken.name,
                        "$callbackCreateMessage|$callbackWCoin|$fromHandlerName" to gameNameAttributes.wCoin.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackFlareX|$fromHandlerName" to gameNameAttributes.flareX.name,
                        "$callbackCreateMessage|$callbackShuttle|$fromHandlerName" to gameNameAttributes.shuttle.name,
                        "$callbackCreateMessage|$callbackClaytonGame|$fromHandlerName" to gameNameAttributes.claytonGame.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackGatto|$fromHandlerName" to gameNameAttributes.gatto.name,
                        "$callbackCreateMessage|$callbackTronSpaceApp|$fromHandlerName" to gameNameAttributes.tronSpaceApp.name,
                        "$callbackCreateMessage|$callbackMuskEmpire|$fromHandlerName" to gameNameAttributes.muskEmpire.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackTonMining|$fromHandlerName" to gameNameAttributes.tonMining.name,
                        "$callbackCreateMessage|$callbackPixelWallet|$fromHandlerName" to gameNameAttributes.pixelWallet.name,
                        "$callbackCreateMessage|$callbackCatGoldMiner|$fromHandlerName" to gameNameAttributes.catGoldMiner.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackNotcoin|$fromHandlerName" to gameNameAttributes.notcoin.name,
                        "$callbackCreateMessage|$callbackCityHolderGame|$fromHandlerName" to gameNameAttributes.cityHolderGame.name,
                        "$callbackCreateMessage|$callbackFrogFarm|$fromHandlerName" to gameNameAttributes.frogFarm.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackYescoinWhite|$fromHandlerName" to gameNameAttributes.yescoinWhite.name,
                        "$callbackCreateMessage|$callbackBrrrrrGame|$fromHandlerName" to gameNameAttributes.brrrrrGame.name,
                        "$callbackCreateMessage|$callbackTimecoinMineYourTime|$fromHandlerName" to gameNameAttributes.timecoinMineYourTime.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackDogs|$fromHandlerName" to gameNameAttributes.dogs.name,
                        "$callbackCreateMessage|$callbackZavodWallet|$fromHandlerName" to gameNameAttributes.zavodWallet.name,
                        "$callbackCreateMessage|$callbackPixelTapByPixelverse|$fromHandlerName" to gameNameAttributes.pixelTapByPixelverse.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackLimeCoin|$fromHandlerName" to gameNameAttributes.limeCoin.name,
                        "$callbackCreateMessage|$callbackTimeFarm|$fromHandlerName" to gameNameAttributes.timeFarm.name,
                        "$callbackCreateMessage|$callbackDJDOG|$fromHandlerName" to gameNameAttributes.djDog.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackYescoinYellow|$fromHandlerName" to gameNameAttributes.yescoinYellow.name,
                        "$callbackCreateMessage|$callbackPretonDrop|$fromHandlerName" to gameNameAttributes.pretonDrop.name,
                        "$callbackCreateMessage|$callbackHotWallet|$fromHandlerName" to gameNameAttributes.hotWallet.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackTonStation|$fromHandlerName" to gameNameAttributes.tonStation.name,
                        "$callbackCreateMessage|$callbackTonBitcoinTBTC|$fromHandlerName" to gameNameAttributes.tonBitcoinTBTC.name,
                        "$callbackCreateMessage|$callbackBlum|$fromHandlerName" to gameNameAttributes.blum.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackCatizen|$fromHandlerName" to gameNameAttributes.catizen.name,
                        "$callbackCreateMessage|$callbackBitton|$fromHandlerName" to gameNameAttributes.bitton.name,
                        "$callbackCreateMessage|$callbackVertus|$fromHandlerName" to gameNameAttributes.vertus.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackSeedApp|$fromHandlerName" to gameNameAttributes.seedApp.name,
                        "$callbackCreateMessage|$callbackMemeFiCoin|$fromHandlerName" to gameNameAttributes.memeFiCoin.name,
                        "$callbackCreateMessage|$callbackBcoin2048|$fromHandlerName" to gameNameAttributes.bcoin2048.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackDuckCoin|$fromHandlerName" to gameNameAttributes.duckCoin.name,
                        "$callbackCreateMessage|$callbackDotcoin|$fromHandlerName" to gameNameAttributes.dotcoin.name,
                        "$callbackCreateMessage|$callbackCubes|$fromHandlerName" to gameNameAttributes.cubes.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackXBlastApp|$fromHandlerName" to gameNameAttributes.xBlastApp.name,
                        "$callbackCreateMessage|$callbackOgCommunityBot|$fromHandlerName" to gameNameAttributes.ogCommunityBot.name,
                        "$callbackCreateMessage|$callbackTapSwap|$fromHandlerName" to gameNameAttributes.tapSwap.name,
                    ),
                    listOf(
                        "$callbackCreateMessage|$callbackFuelMining|$fromHandlerName" to gameNameAttributes.fuelMining.name,
                        "$callbackCreateMessage|$callbackHoldWallet|$fromHandlerName" to gameNameAttributes.holdWallet.name,
                    ),
                    listOf(
                        "$callbackBack|create_new_post" to "Назад",
                    ),

                    )
            )
        )
    }
}