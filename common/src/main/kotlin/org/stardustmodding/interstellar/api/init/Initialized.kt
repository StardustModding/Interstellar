package org.stardustmodding.interstellar.api.init

import org.stardustmodding.interstellar.impl.Interstellar

interface Initialized<T> {
    fun init(it: T)

    fun <I> initChild(it: T, child: I) where I : Initialized<T> {
        Interstellar.LOGGER.info("Initializing system: ${child.javaClass.simpleName}")

        child.init(it)
    }
}
