package org.stardustmodding.interstellar.impl.init

import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.starsystem.Sol

object StarSystems {
    val SOL = InterstellarRegistries.STAR_SYSTEMS.register(Interstellar.id("sol")) { Sol() }.key!!
}
