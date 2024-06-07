package org.stardustmodding.interstellar.api.util

class DoubleKeyedMap<K1, K2, V> {
    private val map = mutableMapOf<Pair<K1, K2>, V>()
    private val k1 = mutableMapOf<K1, MutableList<Pair<Pair<K1, K2>, V>>>()
    private val k2 = mutableMapOf<K2, MutableList<Pair<Pair<K1, K2>, V>>>()

    operator fun set(key: Pair<K1, K2>, value: V) {
        map[key] = value

        k1.getOrDefault(key.first, mutableListOf()).add(Pair(key, value))
        k2.getOrDefault(key.second, mutableListOf()).add(Pair(key, value))
    }

    operator fun get(key: Pair<K1, K2>): V? {
        return map[key]
    }

    operator fun get(key: K1): MutableList<Pair<Pair<K1, K2>, V>> {
        return k1.getOrDefault(key, mutableListOf())
    }

    operator fun get(key: K2): MutableList<Pair<Pair<K1, K2>, V>> {
        return k2.getOrDefault(key, mutableListOf())
    }

    fun remove(key: Pair<K1, K2>) {
        if (key !in map) return

        val it = map.remove(key)

        k1[key.first]?.remove(Pair(key, it))
        k2[key.second]?.remove(Pair(key, it))
    }

    operator fun contains(key: Pair<K1, K2>): Boolean {
        return map.containsKey(key)
    }
}
