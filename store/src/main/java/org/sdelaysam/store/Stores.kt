package org.sdelaysam.store

import org.sdelaysam.store.store.HashMapStore
import org.sdelaysam.store.store.LinearTransactionalStore

object Stores {

    fun linear(factory: Store.Factory = HashMapStore.Factory()): TransactionalStore {
        return LinearTransactionalStore(factory)
    }

}