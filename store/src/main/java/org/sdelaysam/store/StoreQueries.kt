package org.sdelaysam.store

interface StoreQueries {
    fun countKeysByValue(value: String): Int
}