package org.stardustmodding.interstellar.api.data

class Cache<T> {
    private var value: T? = null

    fun get(fallback: () -> (T?)): T? {
        if (value == null) {
            value = fallback()
        }

        return value
    }

    fun set(value: T) {
        this.value = value
    }
}