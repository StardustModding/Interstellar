package org.stardustmodding.skyengine.sim

import com.google.common.collect.Sets
import kotlinx.coroutines.*
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import org.stardustmodding.skyengine.data.WorldExt.getPressureState
import org.stardustmodding.skyengine.data.WorldExt.savePressureState
import org.stardustmodding.skyengine.data.WorldExt.setPressureState
import org.stardustmodding.skyengine.math.PosExt.getRelative
import org.stardustmodding.skyengine.math.PosExt.toVec

/**
 * A multithreaded, asynchronous, and fast handler for pressure updates
 * within a configurable (default 500x500) block radius.
 *
 * All direct disk writes (updating actual states in the world's files)
 * are deferred until the very end to prevent thousands of unnecessary
 * writes.
 *
 * Using Kotlin's coroutines API, this entire system has updates run in
 * parallel, maximizing the speed and efficiency of the algorithm.
 *
 * ### Note
 * Every iteration **MUST** be run *sequentially*, as otherwise there is
 * a high risk of a [ConcurrentModificationException].
 */
class PressureUpdater {
    private val checked = Sets.newConcurrentHashSet<BlockPos>()
    private var origin: BlockPos? = null

    fun start(
        pos: BlockPos,
        world: ServerWorld,
        iterations: Int = DEFAULT_ITERATIONS,
        maxDist: Double = DEFAULT_MAX_DISTANCE
    ) = runBlocking {
        origin = pos
        checked.clear()
        updateSingleBlock(pos, world, maxDist)

        for (i in 0..iterations) {
            val newStart = checked.last()

            origin = newStart
            checked.clear()
            updateSingleBlock(newStart, world, maxDist)
        }

        checked.clear()
        world.savePressureState()
        origin = null
    }

    private suspend fun updateSingleBlock(pos: BlockPos, world: ServerWorld, maxDist: Double) {
        if (!world.getBlockState(pos).isAir) return
        if (pos !in checked) checked.add(pos)

        val state = world.getPressureState(pos, true)
        val up = pos.getRelative(Direction.UP)
        val down = pos.getRelative(Direction.DOWN)
        val north = pos.getRelative(Direction.NORTH)
        val east = pos.getRelative(Direction.EAST)
        val south = pos.getRelative(Direction.SOUTH)
        val west = pos.getRelative(Direction.WEST)
        val split = mutableListOf<Pair<BlockPos, GasComposition>>()

        for (item in listOf(up, down, north, east, south, west)) {
            if (item in checked || !world.getBlockState(item).isAir || !item.isWithinDistance(
                    origin?.toVec(),
                    maxDist
                )
            ) continue
            val it = world.getPressureState(item, true)

            split.add(Pair(item, it))
            checked.add(item)
        }

        if (split.size <= 0) return

        for (gas in state) {
            var diff = 0f

            for (item in split) {
                diff += state[gas] - item.second[gas]
            }

            val remove = state[gas] * (1f / diff)

            for (item in split) {
                val amnt = (item.second[gas] / diff) * remove

                item.second[gas] = item.second[gas] + amnt
                world.setPressureState(item.first, item.second, true)
            }

            state[gas] = state[gas] - remove
        }

        withContext(Dispatchers.Default) {
            split.map {
                async { updateSingleBlock(it.first, world, maxDist) }
            }.awaitAll()
        }
    }

    companion object {
        const val DEFAULT_MAX_DISTANCE = 500.0
        const val DEFAULT_ITERATIONS = 3
    }
}
