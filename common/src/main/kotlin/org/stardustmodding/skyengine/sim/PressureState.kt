package org.stardustmodding.skyengine.sim

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.nbt.NbtUtils
import net.minecraft.nbt.ListTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.core.BlockPos
import org.stardustmodding.interstellar.api.data.SavedState
import org.stardustmodding.skyengine.SkyEngine.id

class PressureState : SavedState() {
    private val states = mutableMapOf<BlockPos, GasComposition>()
    private var dirty = false

    operator fun set(pos: BlockPos, value: GasComposition) = states.set(pos, value)

    fun getOrPut(pos: BlockPos, supplier: () -> GasComposition): GasComposition {
        if (pos in states) {
            return states[pos]!!
        }

        states[pos] = supplier()
        dirty = true

        return states[pos]!!
    }

    override fun read(tag: CompoundTag): SavedState {
        val list = tag.getList("data", Tag.TAG_COMPOUND.toInt())
        val me = PressureState()

        for (el in list) {
            val comp = el as CompoundTag
            val pos = NbtUtils.readBlockPos(comp.getCompound("pos"))
            val gcomp = GasComposition().read(comp.getCompound("data"))

            me.states[pos] = gcomp
        }

        me.dirty = true

        return me
    }

    override fun write(nbt: CompoundTag): CompoundTag {
        val list = ListTag()

        for ((key, value) in states) {
            val comp = CompoundTag()

            comp.put("pos", NbtUtils.writeBlockPos(key))
            comp.put("data", value.write())

            list.add(comp)
        }

        nbt.put("data", list)
        dirty = false

        return nbt
    }

    override fun default(): SavedState = PressureState()
    override val id: ResourceLocation = id("pressure_state")
    override val shouldWrite: Boolean get() = dirty
}
