package org.sdelaysam.store.store

import org.sdelaysam.store.Store

internal class HashMapStore(parentStore: Store?) : BaseStore(parentStore) {

    private val map = mutableMapOf<String, StoreItem>()

    override fun setItem(key: String, entry: StoreItem) {
        map[key] = entry
    }

    override fun getItem(key: String): StoreItem? {
        return map[key]
    }

    override fun getEntries(): Iterable<Map.Entry<String, StoreItem>> {
        return map.entries
    }

    internal class Factory: Store.Factory {
        override fun create(parentStore: Store?): Store {
            return HashMapStore(parentStore)
        }
    }
}