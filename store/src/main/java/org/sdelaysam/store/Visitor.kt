package org.sdelaysam.store

// Main point of library extensions.
// Meant to implement custom operations over transactional store.
internal interface Visitor {
    fun visitStore(store: TransactionalStoreImpl)
    fun visitTransaction(transaction: Transaction)
}