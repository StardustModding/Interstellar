package org.stardustmodding.interstellar.impl.client.gui.suit

import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import org.stardustmodding.interstellar.impl.init.Screens

@Suppress("UNUSED_PARAMETER")
class CustomizationScreenHandler(syncId: Int) : AbstractContainerMenu(Screens.SUIT_CUSTOMIZATION_HANDLER, syncId) {
    constructor(syncId: Int, inv: Inventory) : this(syncId)

    override fun quickMoveStack(player: Player, slot: Int): ItemStack? {
        return null
    }

    override fun stillValid(player: Player): Boolean {
        return true
    }
}
