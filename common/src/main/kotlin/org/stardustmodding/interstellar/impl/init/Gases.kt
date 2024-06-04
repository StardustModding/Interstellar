package org.stardustmodding.interstellar.impl.init

import net.minecraft.text.Text.translatable
import org.stardustmodding.interstellar.api.gas.Gas
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.impl.Interstellar.id

object Gases {
    val OXYGEN =
        InterstellarRegistries.GASES.register(id("oxygen")) {
            Gas(translatable("interstellar.gas.oxygen"), true)
        }.key!!

    val CARBON_DIOXIDE =
        InterstellarRegistries.GASES.register(id("carbon_dioxide")) {
            Gas(translatable("interstellar.gas.carbon_dioxide"))
        }.key!!

    val NITROGEN =
        InterstellarRegistries.GASES.register(id("nitrogen")) {
            Gas(translatable("interstellar.gas.nitrogen"))
        }.key!!

    val ARGON =
        InterstellarRegistries.GASES.register(id("argon")) {
            Gas(translatable("interstellar.gas.argon"))
        }.key!!

    val HELIUM =
        InterstellarRegistries.GASES.register(id("helium")) {
            Gas(translatable("interstellar.gas.helium"))
        }.key!!

    val METHANE =
        InterstellarRegistries.GASES.register(id("methane")) {
            Gas(translatable("interstellar.gas.methane"))
        }.key!!

    val HYDROGEN =
        InterstellarRegistries.GASES.register(id("hydrogen")) {
            Gas(translatable("interstellar.gas.hydrogen"))
        }.key!!

    val AMMONIA =
        InterstellarRegistries.GASES.register(id("ammonia")) {
            Gas(translatable("interstellar.gas.ammonia"))
        }.key!!
}
