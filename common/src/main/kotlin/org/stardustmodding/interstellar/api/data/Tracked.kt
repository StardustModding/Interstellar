package org.stardustmodding.interstellar.api.data

class Tracked<T>(private var value: T) {
    private val subscribers: MutableSet<(T) -> Unit> = mutableSetOf()

    fun subscribe(subscriber: () -> Unit) {
        subscribers.add { subscriber() }
    }

    fun subscribe(subscriber: (T) -> Unit) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: (T) -> Unit) {
        subscribers.remove(subscriber)
    }

    fun update(cb: (T) -> T) {
        value = cb(value)
        submit()
    }

    fun updateSilently(cb: (T) -> T) {
        value = cb(value)
    }

    fun submit() {
        subscribers.forEach {
            it(value)
        }
    }

    fun get(): T = value
}