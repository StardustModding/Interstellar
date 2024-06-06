package org.stardustmodding.interstellar.impl.client.gui.suit

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import org.stardustmodding.interstellar.impl.init.Screens

class CustomizationScreenHandler(syncId: Int) : ScreenHandler(Screens.SUIT_CUSTOMIZATION_HANDLER, syncId) {
    constructor(syncId: Int, inv: PlayerInventory) : this(syncId)

    override fun quickMove(player: PlayerEntity?, slot: Int): ItemStack? {
        return null
    }

    override fun canUse(player: PlayerEntity?): Boolean {
        return true
    }
}
