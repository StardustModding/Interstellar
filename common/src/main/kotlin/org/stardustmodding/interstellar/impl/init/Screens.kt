package org.stardustmodding.interstellar.impl.init

import dev.architectury.registry.menu.MenuRegistry
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.stardustmodding.interstellar.impl.Interstellar.id
import org.stardustmodding.interstellar.impl.client.gui.suit.CustomizationScreenHandler

@Suppress("DEPRECATION", "removal")
object Screens {
    val SUIT_CUSTOMIZATION_HANDLER = Registry.register(
        Registries.SCREEN_HANDLER,
        id("suit_customization"),
        MenuRegistry.of(::CustomizationScreenHandler)
    )!!
}
