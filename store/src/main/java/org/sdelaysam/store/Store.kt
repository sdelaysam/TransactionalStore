package org.sdelaysam.store

// core interface for the store
interface Store {
    fun setValue(key: String, value: String)
    fun getValue(key: String): String?
    fun deleteKey(key: String)
}