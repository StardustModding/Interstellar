package org.stardustmodding.interstellar.impl.client.gui.suit

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import org.stardustmodding.interstellar.api.client.gui.SharedScreen

@Suppress("UNUSED_PARAMETER")
class CustomizationScreen(handler: CustomizationScreenHandler) : SharedScreen<CustomizationScreenHandler>(
    handler,
    Component.translatable("interstellar.screens.suit.customization"),
    600,
    400
) {
    constructor(handler: CustomizationScreenHandler, inv: Inventory, title: Component) : this(handler)
}
