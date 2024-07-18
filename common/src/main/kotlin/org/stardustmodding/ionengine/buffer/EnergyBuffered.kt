package org.stardustmodding.ionengine.buffer

import net.minecraft.nbt.ListTag
import net.minecraft.server.MinecraftServer
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.ionengine.operation.EnergyOperation

interface EnergyBuffered {
    var buffer: Float
    val operationQueue: MutableList<EnergyOperation>

    fun queue(op: EnergyOperation) {
        operationQueue.add(op)
    }

    fun tick(server: MinecraftServer) {
        for (op in operationQueue) {
            if (!op.started) {
                op.started = true
                op.started(server)
            } else if (!op.indefinite && op.needs <= buffer) {
                op.ended(server)
                operationQueue.remove(op)
            } else if ((op.perTick != null && op.perTick!! <= buffer) || op.needs <= buffer) {
                // We don't want `started` and `tick` to run on the same tick, so put
                // it in an else block
                op.tick(server)
            }
        }
    }

    fun save(): ListTag {
        val list = ListTag()

        operationQueue.forEach { list.add(it.write()) }

        return list
    }

    fun load(list: ListTag) {
        for (item in list) {
            val it = EnergyOperation.tryLoadOperation(ResourceLocation.tryParse(item.asString)!!)

            operationQueue.add(it)
        }
    }
}
