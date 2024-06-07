package org.stardustmodding.interstellar.api.physics

import net.minecraft.server.MinecraftServer
import org.stardustmodding.interstellar.api.event.Ticked
import org.stardustmodding.interstellar.api.init.Deinitialized
import org.stardustmodding.interstellar.api.init.InitializedServer
import org.stardustmodding.interstellar.impl.Interstellar.LOGGER
import physx.PxTopLevelFunctions
import physx.common.*
import physx.physics.*
import kotlin.math.min

object Physics : InitializedServer, Deinitialized, Ticked {
    private val rawVersion = PxTopLevelFunctions.getPHYSICS_VERSION()
    private val numThreads = min(4, Runtime.getRuntime().availableProcessors())

    private val version: String
        get() {
            return "${rawVersion shr 24}.${(rawVersion shr 16) and 0xff}.${(rawVersion shr 8) and 0xff}"
        }

    private val gravity = PxVec3(0f, -9.82f, 0f)
    private var alloc: PxDefaultAllocator? = null
    private var err: PxDefaultErrorCallback? = null
    private var foundation: PxFoundation? = null
    private var tolerances: PxTolerancesScale? = null
    private var dispatcher: PxDefaultCpuDispatcher? = null
    private var sceneDesc: PxSceneDesc? = null

    @JvmField
    var physics: PxPhysics? = null

    @JvmField
    var scene: PxScene? = null

    @JvmField
    var material: PxMaterial? = null

    @JvmField
    var shapeFlags: PxShapeFlags? = null

    override fun init(it: MinecraftServer) {
        LOGGER.info("PhysX Loaded! Version: $version")

        alloc = PxDefaultAllocator()
        err = PxDefaultErrorCallback()
        foundation = PxTopLevelFunctions.CreateFoundation(rawVersion, alloc, err)
        tolerances = PxTolerancesScale()
        physics = PxTopLevelFunctions.CreatePhysics(rawVersion, foundation, tolerances)
        dispatcher = PxTopLevelFunctions.DefaultCpuDispatcherCreate(numThreads)
        sceneDesc = PxSceneDesc(tolerances)
        sceneDesc!!.gravity = gravity
        sceneDesc!!.cpuDispatcher = dispatcher
        sceneDesc!!.filterShader = PxTopLevelFunctions.DefaultFilterShader()
        scene = physics!!.createScene(sceneDesc)
        scene!!.gravity = gravity
        material = physics!!.createMaterial(0.5f, 0.5f, 0.5f)
        shapeFlags =
            PxShapeFlags((PxShapeFlagEnum.eSCENE_QUERY_SHAPE.value or PxShapeFlagEnum.eSIMULATION_SHAPE.value).toByte())
    }

    override fun deinit() {
        LOGGER.info("Destroying PhysX...")

        scene?.release()
        material?.release()
        physics?.release()
        foundation?.release()
        err?.destroy()
        alloc?.destroy()

        LOGGER.info("PhysX destroyed!")
    }

    override fun tick(server: MinecraftServer, delta: Float) {
        scene?.simulate(delta)
        scene?.fetchResults(true)
    }
}
