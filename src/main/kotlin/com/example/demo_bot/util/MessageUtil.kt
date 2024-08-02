package com.example.demo_bot.util

import com.example.demo_bot.model.BotAttributes
import com.example.demo_bot.model.GameNameAttributes
import com.example.demo_bot.model.enums.HandlerGamesName
import com.example.demo_bot.model.enums.HandlerGamesName.*
import com.example.demo_bot.model.enums.HandlerName
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

/**
 * Ф-ия в которой мы создаём объект SendMessage с указанием текста и chatId.
 * Также включаем поддержку форматирования текста с помощью markdown и запрещаем отображение превью для внешних ссылок,
 * если таковые будут (просто чтобы не загромождать сообщение).
 */
fun createMessage(chatId: String, text: String) =
    SendMessage(chatId, text)
        .apply { enableMarkdown(true) }
        .apply { disableWebPagePreview() }

fun createDialogMenu(
    chatId: String,
    text: String,
    inlineButtons: List<List<Pair<String, String>>>,
    fromHandlerName: HandlerName
) =
    createMessage(chatId, text).apply {
        replyMarkup = getInlineKeyboard(inlineButtons, fromHandlerName)
    }

fun getInlineKeyboard(
    allButtons: List<List<Pair<String, String>>>,
    fromHandlerName: HandlerName
): InlineKeyboardMarkup =
    InlineKeyboardMarkup().apply {
        keyboard = allButtons.map { rowButtons ->
            rowButtons.map { (data, buttonText) ->
                InlineKeyboardButton().apply {
                    text = buttonText
                    callbackData = "$data|${fromHandlerName.text}"
                }
            }
        }
    }

fun sendMessage(attributes: BotAttributes, listHashTags: List<String>, chatId: String, message: String) =
    createMessage(
        chatId,
        previewMessage(attributes, listHashTags, message)
    )

fun previewMessage(attributes: BotAttributes, listHashTags: List<String>, message: String) =
    """
          [${attributes.attributes.headName}]${attributes.attributes.headLink}
            
          $message      
                                            
          $listHashTags
                                
          ${attributes.attributesLink}
        """.trimIndent()

fun findNameGameAndLink(param: String, game: GameNameAttributes): String {
    var nameAndLink = param

    when (param) {

        KGB.text -> nameAndLink = "[${game.kgb.name}]${game.kgb.link}"
        PLAY_CORN_BATTLES.text -> nameAndLink = "[${game.playCornBattles.name}]${game.playCornBattles.link}"
        HAMSTERDAM.text -> nameAndLink = "[${game.hamsterdam.name}]${game.hamsterdam.link}"
        ZAR_GATES.text -> nameAndLink = "[${game.zarGates.name}]${game.zarGates.link}"
        QAPPI_MINER.text -> nameAndLink = "[${game.qappiMiner.name}]${game.qappiMiner.link}"
        POCKET_FI.text -> nameAndLink = "$[game.pocketFi.name]${game.pocketFi.link}"
        CEX_IO_POWER_TAP.text -> nameAndLink = "$[game.cexIOPowerTap.name]${game.cexIOPowerTap.link}"
        SPHINX.text -> nameAndLink = "$[game.sphinx.name]${game.sphinx.link}"
        BET_FURY.text -> nameAndLink = "$[game.betFury.name]${game.betFury.link}"
        HARVEST_MOON.text -> nameAndLink = "[${game.harvestMoon.name}]${game.harvestMoon.link}"
        OKX_RACER.text -> nameAndLink = "[${game.okxRacer.name}]${game.okxRacer.link}"
        RICH_ANIMALS.text -> nameAndLink = "[${game.richAnimals.name}]${game.richAnimals.link}"
        SIGNIE.text -> nameAndLink = "[${game.signie.name}]${game.signie.link}"
        HAMSTER_COMBAT.text -> nameAndLink = "[${game.hamsterKombat.name}]${game.hamsterKombat.link}"
        MTK_CLICKER_MAFIA.text -> nameAndLink = "[${game.mtkClickerMafia.name}]${game.mtkClickerMafia.link}"
        NOTCOIN.text -> nameAndLink = "[${game.notcoin.name}]${game.notcoin.link}"
        EARTH_COIN.text -> nameAndLink = "[${game.earthCoin.name}]${game.earthCoin.link}"
        WIN_TOKEN.text -> nameAndLink = "[${game.winToken.name}]${game.winToken.link}"
        W_COIN.text -> nameAndLink = "[${game.wCoin.name}]${game.wCoin.link}"
        FLARE_X.text -> nameAndLink = "[${game.flareX.name}]${game.flareX.link}"
        SHUTTLE.text -> nameAndLink = "[${game.shuttle.name}]${game.shuttle.link}"
        CLAYTON_GAME.text -> nameAndLink = "[${game.claytonGame.name}]${game.claytonGame.link}"
        GATTO.text -> nameAndLink = "[${game.gatto.name}]${game.gatto.link}"
        TRON_SPACE_APP.text -> nameAndLink = "[${game.tronSpaceApp.name}]${game.tronSpaceApp.link}"
        MUSK_EMPIRE.text -> nameAndLink = "[${game.muskEmpire.name}]${game.muskEmpire.link}"
        TON_MINING.text -> nameAndLink = "[${game.tonMining.name}]${game.tonMining.link}"
        PIXEL_WALLET.text -> nameAndLink = "[${game.pixelWallet.name}]${game.pixelWallet.link}"
        CAT_GOLD_MINER.text -> nameAndLink = "[${game.catGoldMiner.name}]${game.catGoldMiner.link}"
        CITY_HOLDER_GAME.text -> nameAndLink = "[${game.cityHolderGame.name}]${game.cityHolderGame.link}"
        FROG_FARM.text -> nameAndLink = "[${game.frogFarm.name}]${game.frogFarm.link}"
        YESCOIN_WHITE.text -> nameAndLink = "[${game.yescoinWhite.name}]${game.yescoinWhite.link}"
        BRRRRR_GAME.text -> nameAndLink = "[${game.brrrrrGame.name}]${game.brrrrrGame.link}"
        TIMECOIN_MINE_YOUR_TIME.text -> nameAndLink = "[${game.timecoinMineYourTime.name}]${game.timecoinMineYourTime.link}"
        DOGS.text -> nameAndLink = "[${game.dogs.name}]${game.dogs.link}"
        ZAVOD_WALLET.text -> nameAndLink = "[${game.zavodWallet.name}]${game.zavodWallet.link}"
        PIXEL_TAP_BY_PIXELVERCE.text -> nameAndLink = "$[game.pixelTapByPixelverse.name]${game.pixelTapByPixelverse.link}"
        LIME_COIN.text -> nameAndLink = "[${game.limeCoin.name}]${game.limeCoin.link}"
        TIME_FARM.text -> nameAndLink = "[${game.timeFarm.name}]${game.timeFarm.link}"
        DJ_DOG.text -> nameAndLink = "[${game.djDog.name}]${game.djDog.link}"
        YESCOIN_YELLOW.text -> nameAndLink = "[${game.yescoinYellow.name}]${game.yescoinYellow.link}"
        PRETON_DROP.text -> nameAndLink = "[${game.pretonDrop.name}]${game.pretonDrop.link}"
        HOT_WALLET.text -> nameAndLink = "[${game.hotWallet.name}]${game.hotWallet.link}"
        TON_STATION.text -> nameAndLink = "[${game.tonStation.name}]${game.tonStation.link}"
        TON_BITCOIN_TBTC.text -> nameAndLink = "[${game.tonBitcoinTBTC.name}]${game.tonBitcoinTBTC.link}"
        BLUM.text -> nameAndLink = "[${game.blum.name}]${game.blum.link}"
        CATIZEN.text -> nameAndLink = "[${game.catizen.name}]${game.catizen.link}"
        BITTON.text -> nameAndLink = "[${game.bitton.name}]${game.bitton.link}"
        VERTUS.text -> nameAndLink = "[${game.vertus.name}]${game.vertus.link}"
        SEED_APP.text -> nameAndLink = "[${game.seedApp.name}]${game.seedApp.link}"
        MEME_FI_COIN.text -> nameAndLink = "[${game.memeFiCoin.name}]${game.memeFiCoin.link}"
        BCOIN_2048.text -> nameAndLink = "[${game.bcoin2048.name}]${game.bcoin2048.link}"
        DUCK_COIN.text -> nameAndLink = "[${game.duckCoin.name}]${game.duckCoin.link}"
        DOT_COIN.text -> nameAndLink = "[${game.dotcoin.name}]${game.dotcoin.link}"
        CUBES.text -> nameAndLink = "[${game.cubes.name}]${game.cubes.link}"
        X_BLAST_APP.text -> nameAndLink = "[${game.xBlastApp.name}]${game.xBlastApp.link}"
        OG_COMMUNITY_BOT.text -> nameAndLink = "[${game.ogCommunityBot.name}]${game.ogCommunityBot.link}"
        TAP_SWAP.text -> nameAndLink = "[${game.tapSwap.name}]${game.tapSwap.link}"
        FUEL_MINING.text -> nameAndLink = "[${game.fuelMining.name}]${game.fuelMining.link}"
        HOLD_WALLET.text -> nameAndLink = "[${game.holdWallet.name}]${game.holdWallet.link}"
    }
    return nameAndLink
}