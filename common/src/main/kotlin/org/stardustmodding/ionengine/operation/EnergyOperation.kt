package org.stardustmodding.ionengine.operation

import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceKey
import net.minecraft.server.MinecraftServer
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.api.data.NbtSerializable
import org.stardustmodding.ionengine.IonEngine.id
import org.stardustmodding.ionengine.registry.IonRegistries
import kotlin.jvm.optionals.getOrNull

/**
 * Metadata about an operation that consumes energy.
 */
interface EnergyOperation: NbtSerializable<EnergyOperation?> {
    /**
     * Whether this operation has started yet.
     */
    var started: Boolean
        get() = STARTED_MAP.getOrPut(id ?: id("empty")) { false }
        set(value) { STARTED_MAP[id ?: id("empty")] = value }

    /**
     * The operation's unique ID.
     */
    val id get() = IonRegistries.OPERATIONS.getResourceKey(this).getOrNull()?.location()

    /**
     * How much energy this operation needs to run.
     * For a looping operation, use the [indefinite] flag.
     */
    val needs: Float

    /**
     * If the whole [needs] buffer isn't full, this is how
     * much it'll use per tick until it can get up to that number.
     *
     * If this is `null`, then it will wait for the whole buffer to
     * be full (up to the [needs] value).
     */
    val perTick: Float? get() = null

    /**
     * Whether this operation is indefinite, meaning it will loop
     * upon completion until the machine is unpowered. This is
     * useful for something like a magnet where it constantly uses
     * energy when it's active.
     */
    val indefinite: Boolean get() = false

    /**
     * Called when this operation gets ticked.
     */
    fun tick(server: MinecraftServer) {}

    /**
     * Called when this operation starts.
     */
    fun started(server: MinecraftServer) {}

    /**
     * Called when this operation ends.
     */
    fun ended(server: MinecraftServer) {}

    /**
     * Check if this operation can run with the given amount of energy.
     */
    fun canRun(energy: Float): Boolean {
        return if (perTick != null) {
            energy >= perTick!!
        } else {
            energy >= needs
        }
    }

    override fun read(tag: Tag): EnergyOperation? {
        return tryLoadOperation(ResourceLocation.tryParse((tag as StringTag).asString)!!)
    }

    override fun write(): Tag = StringTag.valueOf(id.toString())

    /**
     * An energy operation that goes in one shot, like an ore being processed.
     */
    open class Constant(override val needs: Float, override val indefinite: Boolean = false): EnergyOperation

    /**
     * An energy operation that goes every tick that it can.
     */
    open class Ticking(override val perTick: Float, private val duration: Int, override val indefinite: Boolean = false): EnergyOperation {
        override val needs: Float get() = perTick * duration
    }

    companion object {
        private val STARTED_MAP = mutableMapOf<ResourceLocation, Boolean>()

        fun tryLoadOperation(id: ResourceLocation): EnergyOperation {
            return IonRegistries.OPERATIONS.get(ResourceKey.create(IonRegistries.Keys.OPERATIONS_KEY, id))!!
        }
    }
}
