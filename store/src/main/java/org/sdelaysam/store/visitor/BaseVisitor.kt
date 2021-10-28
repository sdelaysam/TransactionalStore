package org.sdelaysam.store.visitor

import org.sdelaysam.store.Store
import org.sdelaysam.store.Visitor
import org.sdelaysam.store.store.BaseStore

internal abstract class BaseVisitor : Visitor {
    override fun visit(store: Store) {
        when (store) {
            is BaseStore -> visitBaseStore(store)
            else -> throw IllegalArgumentException("Unsupported store: $store")
        }
    }

    protected abstract fun visitBaseStore(store: BaseStore)
}