package org.stardustmodding.interstellar.api.starsystem

import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text
import org.stardustmodding.interstellar.api.planet.Planet

open class StarSystem(val name: Text, val planets: MutableList<RegistryKey<out Planet>> = mutableListOf()) {

}
