package org.stardustmodding.interstellar.api.block

import physx.geometry.PxBoxGeometry

class BlockBounds(val width: Float, val height: Float, val depth: Float) {
    fun asGeometry(): PxBoxGeometry {
        return PxBoxGeometry(width / 2, height / 2, depth / 2)
    }
}
