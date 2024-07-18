package org.stardustmodding.interstellar.api.registry

import com.google.common.collect.ImmutableList
import com.mojang.serialization.Lifecycle
import net.minecraft.core.DefaultedMappedRegistry
import net.minecraft.core.DefaultedRegistry
import net.minecraft.core.Holder
import net.minecraft.core.MappedRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.impl.Interstellar.LOGGER

object RegistryUtil {
    @JvmStatic
    fun <T> unregister(registry: Registry<T>, key: ResourceLocation) {
        if (registry.containsKey(key)) {
            if (registry is MappedRegistry<T>) {
                if (registry is DefaultedRegistry<*>) {
                    require(registry.defaultKey != key) { "Cannot remove default value in registry!" }
                }

                val type = registry.byLocation[key]!!.value()
                val byId = registry.byId

                if (byId.size <= registry.toId.getInt(type)) {
                    LOGGER.warn("ID mismatch in registry '{}'", registry.key())
                }

                registry.toId.removeInt(type)

                var success = false
                for (i in byId.indices) {
                    val reference = byId[i]
                    if (reference != null) {
                        if (reference.key().location() == key) {
                            byId[i] = null
                            success = true
                            var max = 0
                            for (i1 in byId.indices) {
                                max = if (byId[i1] != null) i1 else max
                            }
                            byId.size(max + 1)
                            registry.nextId = max
                            break
                        }
                    }
                }

                assert(success)
                registry.byLocation.remove(key)
                registry.byKey.remove(ResourceKey.create(registry.key(), key))
                registry.byValue.remove(type)
                registry.lifecycles.remove(type)

                val base = Lifecycle.stable()

                for (value in registry.lifecycles.values) {
                    base.add(value)
                }

                registry.registryLifecycle = base

                for (holderSet in registry.tags.values) {
                    val list = ImmutableList.builder<Holder<T>>()

                    for (content in holderSet.contents) {
                        if (!content.`is`(key)) list.add(content)
                    }

                    holderSet.contents = list.build()
                }

                registry.byValue.remove(type)
                registry.holdersInOrder = null
            }
        } else {
            LOGGER.warn("Tried to remove non-existent key {}", key)
        }
    }

    @JvmStatic
    fun <T> registerUnfreeze(registry: Registry<T>, id: ResourceLocation, value: T): Holder.Reference<T> {
        if (!registry.containsKey(id)) {
            if (registry.javaClass == MappedRegistry::class.java || registry.javaClass == DefaultedMappedRegistry::class.java) {
                val mapped = registry as MappedRegistry<T>
                val frozen = registry.frozen
                if (frozen) registry.frozen = false
                val ref = mapped.register(ResourceKey.create(registry.key(), id), value!!, Lifecycle.stable())
                if (frozen) registry.freeze()
                checkNotNull(registry.byId[registry.toId.getInt(value)])
                return ref
            } else {
                throw IllegalStateException("Non-vanilla '" + registry.key().location() + "' registry! " + registry.javaClass.name)
            }
        } else {
            LOGGER.warn("Tried to add pre-existing key$id")
            return registry.getHolderOrThrow(ResourceKey.create(registry.key(), id))
        }
    }

    fun <T> registerUnfreezeExact(
        registry: Registry<T?>,
        rawId: Int,
        id: ResourceLocation,
        value: T
    ): Holder.Reference<T?> {
        if (!registry.containsKey(id)) {
            if (registry.javaClass == MappedRegistry::class.java || registry.javaClass == DefaultedMappedRegistry::class.java) {
                val mapped = registry as MappedRegistry<T?>
                val frozen = registry.frozen
                if (frozen) registry.frozen = false
                val ref = mapped.registerMapping(rawId, ResourceKey.create(registry.key(), id), value!!, Lifecycle.stable())
                if (frozen) registry.freeze()
                return ref
            } else {
                throw IllegalStateException("Non-vanilla '" + registry.key().location() + "' registry! " + registry.javaClass.name)
            }
        } else {
            LOGGER.warn(
                "Tried to add pre-existing key $id with raw id $rawId (contains: " + registry.getId(
                    registry[id]
                ) + ")"
            )
            return registry.getHolderOrThrow(ResourceKey.create(registry.key(), id))
        }
    }
}
