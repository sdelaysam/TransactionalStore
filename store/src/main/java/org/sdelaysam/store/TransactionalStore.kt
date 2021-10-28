package org.sdelaysam.store

interface TransactionalStore : Store {
    val queries: StoreQueries
    val inTransaction: Boolean

    fun beginTransaction()
    fun commitTransaction(): Boolean
    fun rollbackTransaction(): Boolean
}