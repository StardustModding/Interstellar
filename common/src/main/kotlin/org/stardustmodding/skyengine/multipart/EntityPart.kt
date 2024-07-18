package org.stardustmodding.skyengine.multipart

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.renderer.LevelRenderer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.Pose
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.util.Mth
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.Vec3


@Suppress("MemberVisibilityCanBePrivate", "LeakingThis", "unused")
open class EntityPart(val owner: Entity, val name: String, val dims: EntityDimensions) : Entity(owner.type, owner.level) {
    var relativePos: Vec3 = Vec3.ZERO
    var pivot: Vec3 = Vec3.ZERO

    constructor(owner: Entity, name: String, width: Float, height: Float) : this(
        owner,
        name,
        EntityDimensions.scalable(width, height)
    )

    init {
        refreshDimensions()
    }

    override fun defineSynchedData() {}
    override fun readAdditionalSaveData(nbt: CompoundTag) {}
    override fun addAdditionalSaveData(nbt: CompoundTag) {}

    override fun isPickable(): Boolean = true
    override fun shouldBeSaved(): Boolean = false
    override fun getPickResult(): ItemStack? = owner.pickResult
    override fun `is`(entity: Entity): Boolean = this == entity || owner == entity
    override fun getAddEntityPacket(): Packet<ClientGamePacketListener> = throw UnsupportedOperationException()
    override fun getDimensions(pose: Pose): EntityDimensions = dims

    override fun hurt(source: DamageSource, amount: Float): Boolean {
        return if (isInvulnerableTo(source)) {
            false
        } else {
            owner.hurt(source, amount)
        }
    }

    val absolutePos = owner.position.add(relativePos)
    val absolutePivot = owner.position.add(pivot)

    fun move(distance: Vec3) {
        move(distance.x, distance.y, distance.z)
    }

    fun move(dx: Double, dy: Double, dz: Double) {
        xOld = this.x
        xo = this.xOld
        yOld = this.y
        yo = this.yOld
        zOld = this.z
        zo = this.zOld

        val newPos = absolutePos.add(dx, dy, dz)

        setPos(newPos)
    }

    fun rotate(pivot: Vec3, pitch: Float, yaw: Float, degrees: Boolean) {
        this.pivot = pivot

        rotate(pitch, yaw, degrees)
    }

    fun rotate(pitch: Float, yaw: Float, degrees: Boolean) {
        var rel = absolutePos.subtract(absolutePivot)

        rel = rel.xRot(-pitch * (if (degrees) Math.PI.toFloat() / 180f else 1f))
            .yRot(-yaw * (if (degrees) Math.PI.toFloat() / 180f else 1f))

        val transformedPos = absolutePivot.subtract(absolutePos).add(rel)

        move(transformedPos)
    }

    @Environment(EnvType.CLIENT)
    fun renderHitbox(
        matrices: PoseStack,
        vertices: VertexConsumer,
        ownerX: Double,
        ownerY: Double,
        ownerZ: Double,
        tickDelta: Float
    ) {
        matrices.pushPose()

        val entityPartX: Double = ownerX + Mth.lerp(tickDelta.toDouble(), xOld, x)
        val entityPartY: Double = ownerY + Mth.lerp(tickDelta.toDouble(), yOld, y)
        val entityPartZ: Double = ownerZ + Mth.lerp(tickDelta.toDouble(), zOld, z)

        matrices.translate(entityPartX, entityPartY, entityPartZ)

        LevelRenderer.renderLineBox(
            matrices,
            vertices,
            boundingBox.move(-x, -y, -z),
            0.25f,
            1.0f,
            0.0f,
            1.0f
        )

        matrices.popPose()
    }
}