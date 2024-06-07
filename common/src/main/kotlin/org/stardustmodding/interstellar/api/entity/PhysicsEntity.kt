package org.stardustmodding.interstellar.api.entity

import com.google.common.collect.Maps
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.network.packet.s2c.play.PositionFlag
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import org.stardustmodding.interstellar.api.block.PhysicsBlock
import org.stardustmodding.interstellar.api.math.McExtensions.clone
import org.stardustmodding.interstellar.api.math.McExtensions.eq
import org.stardustmodding.interstellar.api.math.McExtensions.toPx
import org.stardustmodding.interstellar.api.math.QuatUtil
import org.stardustmodding.interstellar.api.physics.Physics
import org.stardustmodding.interstellar.api.physics.PxQuatExt.toEulerAngles
import org.stardustmodding.interstellar.api.physics.PxVec3Ext.toVec3d
import org.stardustmodding.interstellar.api.physics.TransformExt.pos
import org.stardustmodding.interstellar.api.physics.TransformExt.quat
import org.stardustmodding.interstellar.api.world.WorldExt.getBlocksInRange
import org.stardustmodding.interstellar.impl.Interstellar.LOGGER
import physx.common.PxIDENTITYEnum
import physx.common.PxTransform
import physx.common.PxVec3
import physx.physics.PxActorFlagEnum
import physx.physics.PxRigidDynamic
import physx.physics.PxShape

abstract class PhysicsEntity(type: EntityType<*>?, world: World?) : Entity(type, world) {
    private val transform = PxTransform(PxIDENTITYEnum.PxIdentity)
    private val body: PxRigidDynamic? = Physics.physics?.createRigidDynamic(transform)
    private val shapes: MutableList<PxShape> = mutableListOf()
    private var last = Vec3d.ZERO

    init {
        initPhysics()
    }

    private fun initPhysics() {
        buildShape()
        preInit()
        init()

        body?.wakeUp()
    }

    private fun preInit() {
        Physics.scene?.addActor(body)

        transform.pos = pos.toPx()
        transform.quat = QuatUtil.fromAngles(yaw, 1f, pitch)
    }

    open fun init() {}
    open fun release() {}
    open fun buildShape() {}

    fun attach(shape: PxShape) {
        body?.attachShape(shape)
        shapes.add(shape)
    }

    fun detachAll() {
        for (shape in shapes) {
            body?.detachShape(shape)
            shapes.remove(shape)
        }
    }

    override fun onRemoved() {
        super.onRemoved()

        transform.destroy()
        release()

        for (shape in shapes) {
            shape.release()
        }

        Physics.scene?.removeActor(body)
        body?.release()
    }

    private fun updateWorldColliders() {
        val blocks = world.getBlocksInRange(pos, PHYSICS_UPDATE_RANGE)

        for (item in updated) {
            val ipos = item.key
            val state = item.value

            if (ipos !in blocks) {
                PhysicsBlock.removeCollider(ipos, state)
                updated.remove(ipos)
            }
        }

        for (item in blocks) {
            val ipos = item.key
            val state = item.value

            if (ipos !in updated || updated[ipos] != item.value || !PhysicsBlock.hasCollider(ipos, state)) {
                updated[ipos]?.let { PhysicsBlock.removeCollider(ipos, it) }

                PhysicsBlock.updateCollider(ipos, item.value, world)
            }

            updated[ipos] = item.value
        }
    }

    private fun updateFlags() {
        body?.setActorFlag(PxActorFlagEnum.eDISABLE_GRAVITY, hasNoGravity())
    }

    override fun tick() {
        updateWorldColliders()
        updateFlags()

        if (!pos.equals(last)) {
            LOGGER.info("Entity $uuid position: $pos")
        }

        last = pos.clone()
    }

    override fun teleport(
        world: ServerWorld?,
        destX: Double,
        destY: Double,
        destZ: Double,
        flags: MutableSet<PositionFlag>?,
        yaw: Float,
        pitch: Float
    ): Boolean {
        transform.pos = PxVec3(destX.toFloat(), destY.toFloat(), destZ.toFloat())
        transform.quat = QuatUtil.fromAngles(yaw, 1f, pitch)

        return super.teleport(world, destX, destY, destZ, flags, yaw, pitch)
    }

    companion object {
        const val PHYSICS_UPDATE_RANGE = 4

        // This has to be static so it can be shared for maximum performance
        private val updated = Maps.newConcurrentMap<BlockPos, BlockState>()
    }
}
