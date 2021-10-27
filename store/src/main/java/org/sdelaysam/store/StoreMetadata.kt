package org.sdelaysam.store

// interface for other diverse/specific methods like this
// implementations of which are likely to be put inside visitors
interface StoreMetadata: Store {
    fun countValues(value: String): Int
}