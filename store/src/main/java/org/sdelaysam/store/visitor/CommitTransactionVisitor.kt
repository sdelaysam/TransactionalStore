package org.sdelaysam.store.visitor

import org.sdelaysam.store.Transaction
import org.sdelaysam.store.TransactionalStoreImpl
import org.sdelaysam.store.Visitor

internal class CommitTransactionVisitor : Visitor {

    override fun visitStore(store: TransactionalStoreImpl) = Unit

    override fun visitTransaction(transaction: Transaction) {
        with(transaction) {
            modifications.forEach {
                parentStore.setValue(it.key, it.value)
            }
            deletedKeys.forEach {
                parentStore.deleteKey(it)
            }
        }
    }
}