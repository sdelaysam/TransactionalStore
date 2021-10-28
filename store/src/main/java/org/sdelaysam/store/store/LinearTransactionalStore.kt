package org.sdelaysam.store.store

import org.sdelaysam.store.Store
import org.sdelaysam.store.StoreQueries
import org.sdelaysam.store.TransactionalStore
import org.sdelaysam.store.query.LinearStoreQueries
import org.sdelaysam.store.visitor.CommitTransactionVisitor
import java.util.*

internal class LinearTransactionalStore(
    private val storeFactory: Store.Factory
): TransactionalStore {

    override val queries: StoreQueries by lazy(LazyThreadSafetyMode.NONE) {
        LinearStoreQueries(stores)
    }

    override val inTransaction: Boolean
        get() = stores.size > 1

    private val stores = LinkedList<Store>()

    private val currentStore: Store
        get() {
            require(stores.isNotEmpty()) { "Root store is required" }
            return stores.last
        }

    init {
        stores.add(storeFactory.create(null))
    }

    override fun beginTransaction() {
        stores.add(storeFactory.create(currentStore))
    }

    override fun commitTransaction(): Boolean {
        if (!inTransaction) return false
        CommitTransactionVisitor().visit(stores.removeLast())
        return true
    }

    override fun rollbackTransaction(): Boolean {
        if (!inTransaction) return false
        stores.removeLast()
        return true
    }

    override fun setValue(key: String, value: String) {
        currentStore.setValue(key, value)
    }

    override fun getValue(key: String): String? {
        return currentStore.getValue(key)
    }

    override fun deleteKey(key: String) {
        currentStore.deleteKey(key)
    }

}