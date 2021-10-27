package org.sdelaysam.store.visitor

import org.sdelaysam.store.Transaction
import org.sdelaysam.store.TransactionalStoreImpl
import org.sdelaysam.store.Visitor

internal class KeysForValueVisitor(private val value: String) : Visitor {

    val keys = mutableSetOf<String>()

    override fun visitStore(store: TransactionalStoreImpl) {
        keys.addAll(store.keyValues.filter { it.value == value }.keys)
    }

    override fun visitTransaction(transaction: Transaction) {
        keys.addAll(transaction.modifications.filter { it.value == value }.keys)
        keys.removeAll { transaction.deletedKeys.contains(it) }
    }
}