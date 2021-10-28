package org.sdelaysam.store.store

import org.sdelaysam.store.Store

internal abstract class BaseStore(internal val parent: Store?) : Store {

    internal abstract fun setItem(key: String, entry: StoreItem)

    internal abstract fun getItem(key: String): StoreItem?

    internal abstract fun getEntries(): Iterable<Map.Entry<String, StoreItem>>

    override fun setValue(key: String, value: String) {
        val oldValue = parent?.getValue(key)
        if (oldValue != value) {
            setItem(key, StoreItem(oldValue = oldValue, newValue = value))
        }
    }

    override fun getValue(key: String): String? {
        val item = getItem(key)
        return when {
            item == null -> parent?.getValue(key)
            item.newValue == null -> null
            else -> item.newValue
        }
    }

    override fun deleteKey(key: String) {
        val oldValue = getItem(key)?.newValue ?: parent?.getValue(key)
        if (oldValue != null) {
            setItem(key, StoreItem(oldValue = oldValue, newValue = null))
        }
    }
}