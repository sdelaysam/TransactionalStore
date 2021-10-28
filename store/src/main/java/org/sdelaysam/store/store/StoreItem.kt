package org.sdelaysam.store.store

internal data class StoreItem(
    val value: String,
    val isDeleted: Boolean = false
)