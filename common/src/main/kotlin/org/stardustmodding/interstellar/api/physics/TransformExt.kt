package org.stardustmodding.interstellar.api.physics

import physx.common.PxTransform

object TransformExt {
    var PxTransform.pos get() = p
        set(value) { p = value }

    var PxTransform.quat get() = q
        set(value) { q = value }
}
