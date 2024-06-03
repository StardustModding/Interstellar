package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.NbtCompound
import org.stardustmodding.interstellar.api.data.NbtSerializable


class WaterState : NbtSerializable<WaterState> {
    /**
     * The minimum Y height of oceans based on the terraforming state.
     *
     * @see TerraformationState
     */
    var level = 0f

    /**
     * The depth of the water bodies.
     * These bodies will have their actual sea level at `WaterState.level + WaterState.depth`.
     * This is actually closer to a height for water.
     *
     * @see TerraformationState
     */
    var depth = 0f

    /**
     * The water color.
     */
    var color = 0x617B64

    override fun read(tag: NbtCompound): WaterState {
        val it = WaterState()

        it.level = tag.getFloat("level")
        it.depth = tag.getFloat("depth")
        it.color = tag.getInt("color")

        return it
    }

    override fun write(tag: NbtCompound): NbtCompound {
        tag.putFloat("level", level)
        tag.putFloat("depth", depth)
        tag.putInt("color", color)

        return tag
    }
}
