package org.stardustmodding.interstellar.api.block

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.stardustmodding.interstellar.api.block.BlockUtil.getBlockBounds
import org.stardustmodding.interstellar.api.math.McExtensions.toPx
import org.stardustmodding.interstellar.api.physics.Physics
import org.stardustmodding.interstellar.api.physics.TransformExt.pos
import org.stardustmodding.interstellar.api.util.DoubleKeyedMap
import physx.common.PxIDENTITYEnum
import physx.common.PxTransform
import physx.physics.PxActorFlagEnum
import physx.physics.PxRigidDynamic

object PhysicsBlock {
    private val bodies = DoubleKeyedMap<BlockPos, BlockState, PxRigidDynamic>()

    fun hasCollider(pos: BlockPos, state: BlockState): Boolean {
        return Pair(pos, state) in bodies
    }

    fun removeCollider(pos: BlockPos, state: BlockState) {
        val key = Pair(pos, state)

        if (key in bodies) {
            Physics.scene?.removeActor(bodies[key])
            bodies[key]?.release()
            bodies.remove(key)
        }
    }

    fun updateCollider(pos: BlockPos, state: BlockState, world: World) {
        removeCollider(pos, state)

        for (item in bodies[pos]) {
            removeCollider(item.first.first, item.first.second)
        }

        val key = Pair(pos, state)
        val geom = getBlockBounds(state, world, pos).asGeometry()
        val shape = Physics.physics?.createShape(geom, Physics.material, true, Physics.shapeFlags)
        val transform = PxTransform(PxIDENTITYEnum.PxIdentity)

        transform.pos = pos.toPx()
        bodies[key] = Physics.physics!!.createRigidDynamic(transform)

        bodies[key]?.setActorFlag(PxActorFlagEnum.eDISABLE_GRAVITY, true)
        bodies[key]?.attachShape(shape)

        Physics.scene?.addActor(bodies[key])
    }
}
