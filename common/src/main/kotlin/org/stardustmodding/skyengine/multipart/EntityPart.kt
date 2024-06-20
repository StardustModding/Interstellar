package org.stardustmodding.skyengine.multipart

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityPose
import net.minecraft.entity.damage.DamageSource
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d


@Suppress("MemberVisibilityCanBePrivate", "LeakingThis", "unused")
open class EntityPart(val owner: Entity, val name: String, val dims: EntityDimensions) : Entity(owner.type, owner.world) {
    var relativePos = Vec3d.ZERO!!
    var pivot = Vec3d.ZERO!!

    constructor(owner: Entity, name: String, width: Float, height: Float) : this(
        owner,
        name,
        EntityDimensions.changing(width, height)
    )

    init {
        calculateDimensions()
    }

    override fun initDataTracker() {}
    override fun readCustomDataFromNbt(nbt: NbtCompound?) {}
    override fun writeCustomDataToNbt(nbt: NbtCompound?) {}

    override fun canHit(): Boolean = true
    override fun shouldSave(): Boolean = false
    override fun getPickBlockStack(): ItemStack? = owner.pickBlockStack
    override fun isPartOf(entity: Entity?): Boolean = this == entity || owner == entity
    override fun createSpawnPacket(): Packet<ClientPlayPacketListener> = throw UnsupportedOperationException()
    override fun getDimensions(pose: EntityPose?): EntityDimensions = dims

    override fun damage(source: DamageSource?, amount: Float): Boolean {
        return if (isInvulnerableTo(source)) {
            false
        } else {
            owner.damage(source, amount)
        }
    }

    val absolutePos = owner.pos.add(relativePos)!!
    val absolutePivot = owner.pos.add(pivot)!!

    fun move(distance: Vec3d) {
        move(distance.x, distance.y, distance.z)
    }

    fun move(dx: Double, dy: Double, dz: Double) {
        lastRenderX = this.x
        prevX = this.lastRenderX
        lastRenderY = this.y
        prevY = this.lastRenderY
        lastRenderZ = this.z
        prevZ = this.lastRenderZ

        val newPos = absolutePos.add(dx, dy, dz)

        setPosition(newPos)
    }

    fun rotate(pivot: Vec3d, pitch: Float, yaw: Float, degrees: Boolean) {
        this.pivot = pivot

        rotate(pitch, yaw, degrees)
    }

    fun rotate(pitch: Float, yaw: Float, degrees: Boolean) {
        var rel = absolutePos.subtract(absolutePivot)

        rel = rel.rotateX(-pitch * (if (degrees) Math.PI.toFloat() / 180f else 1f))
            .rotateY(-yaw * (if (degrees) Math.PI.toFloat() / 180f else 1f))

        val transformedPos = absolutePivot.subtract(absolutePos).add(rel)

        move(transformedPos)
    }

    @Environment(EnvType.CLIENT)
    fun renderHitbox(
        matrices: MatrixStack,
        vertices: VertexConsumer,
        ownerX: Double,
        ownerY: Double,
        ownerZ: Double,
        tickDelta: Float
    ) {
        matrices.push()

        val entityPartX: Double = ownerX + MathHelper.lerp(tickDelta.toDouble(), lastRenderX, x)
        val entityPartY: Double = ownerY + MathHelper.lerp(tickDelta.toDouble(), lastRenderY, y)
        val entityPartZ: Double = ownerZ + MathHelper.lerp(tickDelta.toDouble(), lastRenderZ, z)

        matrices.translate(entityPartX, entityPartY, entityPartZ)

        WorldRenderer.drawBox(
            matrices,
            vertices,
            boundingBox.offset(-x, -y, -z),
            0.25f,
            1.0f,
            0.0f,
            1.0f
        )
        matrices.pop()
    }
}