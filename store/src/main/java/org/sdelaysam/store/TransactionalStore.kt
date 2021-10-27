package org.sdelaysam.store

// Main interface to be used outside of the library
interface TransactionalStore: StoreMetadata {
    fun beginTransaction()
    fun commitTransaction(): Boolean
    fun rollbackTransaction(): Boolean

    companion object {
        // might make sense to provide factory instead
        operator fun invoke(): TransactionalStore = TransactionalStoreImpl()
    }
}