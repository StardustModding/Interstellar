package org.stardustmodding.interstellar.impl.init

import net.minecraft.registry.Registry
import net.minecraft.text.Text.translatable
import org.stardustmodding.interstellar.api.gas.Gas
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.impl.Interstellar.id

object Gases {
    val OXYGEN =
        Registry.registerReference(
            InterstellarRegistries.GASES,
            id("oxygen"),
            Gas(translatable("interstellar.gas.oxygen"), true)
        ).key.get()

    val CARBON_DIOXIDE =
        Registry.registerReference(
            InterstellarRegistries.GASES,
            id("carbon_dioxide"),
            Gas(translatable("interstellar.gas.carbon_dioxide"))
        ).key.get()

    val NITROGEN =
        Registry.registerReference(
            InterstellarRegistries.GASES,
            id("nitrogen"),
            Gas(translatable("interstellar.gas.nitrogen"))
        ).key.get()

    val ARGON =
        Registry.registerReference(
            InterstellarRegistries.GASES,
            id("argon"),
            Gas(translatable("interstellar.gas.argon"))
        ).key.get()

    val HELIUM =
        Registry.registerReference(
            InterstellarRegistries.GASES,
            id("helium"),
            Gas(translatable("interstellar.gas.helium"))
        ).key.get()

    val METHANE =
        Registry.registerReference(
            InterstellarRegistries.GASES,
            id("methane"),
            Gas(translatable("interstellar.gas.methane"))
        ).key.get()

    val HYDROGEN =
        Registry.registerReference(
            InterstellarRegistries.GASES,
            id("hydrogen"),
            Gas(translatable("interstellar.gas.hydrogen"))
        ).key.get()

    val AMMONIA =
        Registry.registerReference(
            InterstellarRegistries.GASES,
            id("ammonia"),
            Gas(translatable("interstellar.gas.ammonia"))
        ).key.get()
}
