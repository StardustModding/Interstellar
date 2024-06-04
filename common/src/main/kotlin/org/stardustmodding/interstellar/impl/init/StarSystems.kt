package org.stardustmodding.interstellar.impl.init

import net.minecraft.registry.Registry
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.starsystem.Sol

object StarSystems {
    val SOL = Registry.registerReference(InterstellarRegistries.STAR_SYSTEMS, Interstellar.id("sol"), Sol()).key.get()
}
