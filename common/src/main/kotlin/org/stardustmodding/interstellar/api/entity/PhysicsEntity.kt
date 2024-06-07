package org.stardustmodding.interstellar.api.entity

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.network.packet.s2c.play.PositionFlag
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.stardustmodding.interstellar.api.math.McExtensions.set
import org.stardustmodding.interstellar.api.math.McExtensions.toPx
import org.stardustmodding.interstellar.api.math.QuatUtil
import org.stardustmodding.interstellar.api.physics.PxQuatExt.toEulerAngles
import org.stardustmodding.interstellar.api.physics.PxVec3Ext.toVec3d
import org.stardustmodding.interstellar.api.physics.TransformExt.pos
import org.stardustmodding.interstellar.api.physics.TransformExt.quat
import org.stardustmodding.interstellar.api.physics.Physics
import physx.common.PxIDENTITYEnum
import physx.common.PxTransform
import physx.common.PxVec3
import physx.physics.PxRigidBody
import physx.physics.PxShape

@Suppress("LeakingThis")
abstract class PhysicsEntity(type: EntityType<*>?, world: World?) : Entity(type, world) {
    val transform = PxTransform(PxIDENTITYEnum.PxIdentity)
    val body: PxRigidBody? = Physics.physics?.createRigidDynamic(transform)
    val shapes: MutableList<PxShape> = mutableListOf()

    init {
        buildShape()
        preInit()
        init()
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

    override fun tick() {
        pos.set(transform.pos.toVec3d())
        rotationVector.set(transform.quat.toEulerAngles().toVec3d())
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
}
