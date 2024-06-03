package org.stardustmodding.dynamicdimensions.impl.platform

import org.stardustmodding.dynamicdimensions.impl.platform.services.PlatformHelper
import java.util.*

object Services {
    @JvmField
    val PLATFORM: PlatformHelper = service(
        PlatformHelper::class.java
    )

    private fun <T> service(clazz: Class<T>): T {
        return ServiceLoader.load(clazz).findFirst()
            .orElseThrow { NullPointerException("Failed to load service for " + clazz.name) }
    }
}
