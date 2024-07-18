package org.stardustmodding.interstellar.impl.init

import dev.architectury.registry.menu.MenuRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import org.stardustmodding.interstellar.impl.Interstellar.id
import org.stardustmodding.interstellar.impl.client.gui.suit.CustomizationScreenHandler

@Suppress("DEPRECATION", "removal")
object Screens {
    val SUIT_CUSTOMIZATION_HANDLER = Registry.register(
        BuiltInRegistries.MENU,
        id("suit_customization"),
        MenuRegistry.of(::CustomizationScreenHandler)
    )
}
