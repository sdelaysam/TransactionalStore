package org.sdelaysam.store.store

import org.sdelaysam.store.Store

internal abstract class BaseStore(internal val parent: Store?) : Store {

    internal abstract fun setItem(key: String, entry: StoreItem)

    internal abstract fun getItem(key: String): StoreItem?

    internal abstract fun getEntries(): Iterable<Map.Entry<String, StoreItem>>

    override fun setValue(key: String, value: String) {
        setItem(key, StoreItem(value = value, isDeleted = false))
    }

    override fun getValue(key: String): String? {
        return getItem(key)
            ?.takeIf { !it.isDeleted }
            ?.let { it.value }
            ?: parent?.getValue(key)
    }

    override fun deleteKey(key: String) {
        val value = getItem(key)?.value ?: parent?.getValue(key)
        if (value != null) {
            setItem(key, StoreItem(value = value, isDeleted = true))
        }
    }
}