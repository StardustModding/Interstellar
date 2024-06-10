package org.stardustmodding.skyengine.sim

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
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

    override fun read(tag: NbtCompound): SavedState {
        val list = tag.getList("data", NbtElement.COMPOUND_TYPE.toInt())
        val me = PressureState()

        for (el in list) {
            val comp = el as NbtCompound
            val pos = NbtHelper.toBlockPos(comp.getCompound("pos"))
            val gcomp = GasComposition().read(comp.getCompound("data"))

            me.states[pos] = gcomp
        }

        me.dirty = true

        return me
    }

    override fun write(nbt: NbtCompound): NbtCompound {
        val list = NbtList()

        for ((key, value) in states) {
            val comp = NbtCompound()

            comp.put("pos", NbtHelper.fromBlockPos(key))
            comp.put("data", value.write())

            list.add(comp)
        }

        nbt.put("data", list)
        dirty = false

        return nbt
    }

    override fun default(): SavedState = PressureState()
    override val id: Identifier = id("pressure_state")
    override val shouldWrite: Boolean get() = dirty
}
