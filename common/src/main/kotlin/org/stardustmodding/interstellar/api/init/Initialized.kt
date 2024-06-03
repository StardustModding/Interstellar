package org.stardustmodding.interstellar.api.init

import org.stardustmodding.interstellar.impl.Interstellar
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

interface Initialized<T> {
    fun init(it: T)

    fun initChild(it: T, child: KClass<out Initialized<T>>) {
        Interstellar.LOGGER.info("Initializing system: ${child.simpleName}")

        child.primaryConstructor!!.call().init(it)
    }
}
