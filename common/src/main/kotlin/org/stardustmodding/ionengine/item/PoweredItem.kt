package org.stardustmodding.ionengine.item

import net.minecraft.item.Item
import org.stardustmodding.ionengine.buffer.EnergyBuffered
import org.stardustmodding.ionengine.operation.EnergyOperation

class PoweredItem(settings: Settings?) : Item(settings), EnergyBuffered {
    override var buffer = 0f
    override val operationQueue = mutableListOf<EnergyOperation>()

    // TODO: NBT
}
