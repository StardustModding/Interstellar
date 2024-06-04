package org.stardustmodding.interstellar.impl.starsystem

import net.minecraft.text.Text
import org.stardustmodding.interstellar.api.starsystem.StarSystem
import org.stardustmodding.interstellar.impl.init.Planets

class Sol: StarSystem(Text.translatable("interstellar.star_system.sol"), mutableListOf(Planets.MOON)) {
}
