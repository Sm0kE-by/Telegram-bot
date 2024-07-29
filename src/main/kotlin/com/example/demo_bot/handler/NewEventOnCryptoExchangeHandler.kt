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
class NewEventOnCryptoExchangeHandler(private val gameNameAttributes: GameNameAttributes) : MyCallbackHandlerBot {

    override val name: HandlerName = HandlerName.NEW_EVENT_ON_CRYPTO_EXCHANGE

    val callbackBack = CommandName.START.text

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
                        "$callbackKGB|create_new_post" to gameNameAttributes.kgb.name,
                        "$callbackPlayCornBattles|create_new_post" to gameNameAttributes.playCornBattles.name,
                        "$callbackHamsterdam|create_new_post" to gameNameAttributes.hamsterdam.name,
                    ),
                    listOf(
                        "$callbackZarGates|create_new_post" to gameNameAttributes.zarGates.name,
                        "$callbackQappiMiner|create_new_post" to gameNameAttributes.qappiMiner.name,
                        "$callbackPocketFi|create_new_post" to gameNameAttributes.pocketFi.name,
                    ),
                    listOf(
                        "$callbackCexIOPowerTap|create_new_post" to gameNameAttributes.cexIOPowerTap.name,
                        "$callbackSphinx|create_new_post" to gameNameAttributes.sphinx.name,
                        "$callbackBetFury|create_new_post" to gameNameAttributes.betFury.name,
                    ),
                    listOf(
                        "$callbackHarvestMoon|create_new_post" to gameNameAttributes.harvestMoon.name,
                        "$callbackOkxRacer|create_new_post" to gameNameAttributes.okxRacer.name,
                        "$callbackRichAnimals|create_new_post" to gameNameAttributes.richAnimals.name,
                    ),
                    listOf(
                        "$callbackSignie|create_new_post" to gameNameAttributes.signie.name,
                        "$callbackHamsterKombat|create_new_post" to gameNameAttributes.hamsterKombat.name,
                        "$callbackMtkClickerMafia|create_new_post" to gameNameAttributes.mtkClickerMafia.name,
                    ),
                    listOf(
                        "$callbackEarthCoin|create_new_post" to gameNameAttributes.earthCoin.name,
                        "$callback1winToken|create_new_post" to gameNameAttributes.winToken.name,
                        "$callbackWCoin|create_new_post" to gameNameAttributes.wCoin.name,
                    ),
                    listOf(
                        "$callbackFlareX|create_new_post" to gameNameAttributes.flareX.name,
                        "$callbackShuttle|create_new_post" to gameNameAttributes.shuttle.name,
                        "$callbackClaytonGame|create_new_post" to gameNameAttributes.claytonGame.name,
                    ),
                    listOf(
                        "$callbackGatto|create_new_post" to gameNameAttributes.gatto.name,
                        "$callbackTronSpaceApp|create_new_post" to gameNameAttributes.tronSpaceApp.name,
                        "$callbackMuskEmpire|create_new_post" to gameNameAttributes.muskEmpire.name,
                    ),
                    listOf(
                        "$callbackTonMining|create_new_post" to gameNameAttributes.tonMining.name,
                        "$callbackPixelWallet|create_new_post" to gameNameAttributes.pixelWallet.name,
                        "$callbackCatGoldMiner|create_new_post" to gameNameAttributes.catGoldMiner.name,
                    ),
                    listOf(
                        "$callbackNotcoin|create_new_post" to gameNameAttributes.notcoin.name,
                        "$callbackCityHolderGame|create_new_post" to gameNameAttributes.cityHolderGame.name,
                        "$callbackFrogFarm|create_new_post" to gameNameAttributes.frogFarm.name,
                    ),
                    listOf(
                        "$callbackYescoinWhite|create_new_post" to gameNameAttributes.yescoinWhite.name,
                        "$callbackBrrrrrGame|create_new_post" to gameNameAttributes.brrrrrGame.name,
                        "$callbackTimecoinMineYourTime|create_new_post" to gameNameAttributes.timecoinMineYourTime.name,
                    ),
                    listOf(
                        "$callbackDogs|create_new_post" to gameNameAttributes.dogs.name,
                        "$callbackZavodWallet|create_new_post" to gameNameAttributes.zavodWallet.name,
                        "$callbackPixelTapByPixelverse|create_new_post" to gameNameAttributes.pixelTapByPixelverse.name,
                    ),
                    listOf(
                        "$callbackLimeCoin|create_new_post" to gameNameAttributes.limeCoin.name,
                        "$callbackTimeFarm|create_new_post" to gameNameAttributes.timeFarm.name,
                        "$callbackDJDOG|create_new_post" to gameNameAttributes.djDog.name,
                    ),
                    listOf(
                        "$callbackYescoinYellow|create_new_post" to gameNameAttributes.yescoinYellow.name,
                        "$callbackPretonDrop|create_new_post" to gameNameAttributes.pretonDrop.name,
                        "$callbackHotWallet|create_new_post" to gameNameAttributes.hotWallet.name,
                    ),
                    listOf(
                        "$callbackTonStation|create_new_post" to gameNameAttributes.tonStation.name,
                        "$callbackTonBitcoinTBTC|create_new_post" to gameNameAttributes.tonBitcoinTBTC.name,
                        "$callbackBlum|create_new_post" to gameNameAttributes.blum.name,
                    ),
                    listOf(
                        "$callbackCatizen|create_new_post" to gameNameAttributes.catizen.name,
                        "$callbackBitton|create_new_post" to gameNameAttributes.bitton.name,
                        "$callbackVertus|create_new_post" to gameNameAttributes.vertus.name,
                    ),
                    listOf(
                        "$callbackSeedApp|create_new_post" to gameNameAttributes.seedApp.name,
                        "$callbackMemeFiCoin|create_new_post" to gameNameAttributes.memeFiCoin.name,
                        "$callbackBcoin2048|create_new_post" to gameNameAttributes.bcoin2048.name,
                    ),
                    listOf(
                        "$callbackDuckCoin|create_new_post" to gameNameAttributes.duckCoin.name,
                        "$callbackDotcoin|create_new_post" to gameNameAttributes.dotcoin.name,
                        "$callbackCubes|create_new_post" to gameNameAttributes.cubes.name,
                    ),
                    listOf(
                        "$callbackXBlastApp|create_new_post" to gameNameAttributes.xBlastApp.name,
                        "$callbackOgCommunityBot|create_new_post" to gameNameAttributes.ogCommunityBot.name,
                        "$callbackTapSwap|create_new_post" to gameNameAttributes.tapSwap.name,
                    ),
                    listOf(
                        "$callbackFuelMining|create_new_post" to gameNameAttributes.fuelMining.name,
                        "$callbackHoldWallet|create_new_post" to gameNameAttributes.holdWallet.name,
                    ),
                    listOf(
                        "$callbackBack|create_new_post" to "Назад",
                    ),

                    )
            )
        )
    }
}