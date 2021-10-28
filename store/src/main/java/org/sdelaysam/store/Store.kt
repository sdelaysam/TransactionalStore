package org.sdelaysam.store

interface Store {
    fun setValue(key: String, value: String)
    fun getValue(key: String): String?
    fun deleteKey(key: String)
    
    interface Factory {
        fun create(parentStore: Store?): Store
    }
}