package org.stardustmodding.ionengine.registry

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleDefaultedRegistry
import org.stardustmodding.ionengine.IonEngine.id
import org.stardustmodding.ionengine.operation.EnergyOperation

object IonRegistries {
    val OPERATIONS_KEY = RegistryKey.ofRegistry<EnergyOperation>(id("operations"))!!

    @JvmField
    val OPERATIONS = SimpleDefaultedRegistry(id("operations").toString(), OPERATIONS_KEY, Lifecycle.experimental(), false)
}
