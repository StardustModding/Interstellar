package org.stardustmodding.ionengine.registry

import com.mojang.serialization.Lifecycle
import net.minecraft.core.DefaultedMappedRegistry
import net.minecraft.resources.ResourceKey
import org.stardustmodding.ionengine.IonEngine.id
import org.stardustmodding.ionengine.operation.EnergyOperation

object IonRegistries {
    object Keys {
        val OPERATIONS_KEY = ResourceKey.createRegistryKey<EnergyOperation>(id("operations"))
    }

    @JvmField
    val OPERATIONS = DefaultedMappedRegistry(id("operations").toString(), Keys.OPERATIONS_KEY, Lifecycle.experimental(), false)
}
