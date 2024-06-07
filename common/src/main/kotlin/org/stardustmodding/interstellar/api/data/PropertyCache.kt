package org.stardustmodding.interstellar.api.data

class PropertyCache<T>(private val getter: () -> T) {
    private var value: T? = null

    fun get(): T {
        if (value == null) {
            value = getter()
        }

        return value!!
    }
}