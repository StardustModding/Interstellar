package org.stardustmodding.interstellar.api.registries

import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.registry.RegistryKey
import org.stardustmodding.interstellar.api.gas.Gas
import org.stardustmodding.interstellar.api.init.Initialized
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.starsystem.StarSystem
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.Interstellar.MOD_ID

object InterstellarRegistries: Initialized<Any?> {
    // Keys

    private val PLANETS_KEY = RegistryKey.ofRegistry<Planet>(Interstellar.id("planets"))!!
    private val STAR_SYSTEMS_KEY = RegistryKey.ofRegistry<StarSystem>(Interstellar.id("star_systems"))!!
    private val GASES_KEY = RegistryKey.ofRegistry<Gas>(Interstellar.id("gases"))!!

    // Registries

    val PLANETS = DeferredRegister.create(MOD_ID, PLANETS_KEY)!!
    val STAR_SYSTEMS = DeferredRegister.create(MOD_ID, STAR_SYSTEMS_KEY)!!
    val GASES = DeferredRegister.create(MOD_ID, GASES_KEY)!!

    override fun init(it: Any?) {
        PLANETS.register()
        STAR_SYSTEMS.register()
        GASES.register()
    }
}
