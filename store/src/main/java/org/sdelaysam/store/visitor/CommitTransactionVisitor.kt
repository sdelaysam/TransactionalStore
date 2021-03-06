package org.sdelaysam.store.visitor

import org.sdelaysam.store.store.BaseStore

internal class CommitTransactionVisitor : BaseVisitor() {

    override fun visitBaseStore(store: BaseStore) {
        store.parent?.let { parent ->
            store.getEntries().forEach {
                val newValue = it.value.newValue
                if (newValue == null) {
                    parent.deleteKey(it.key)
                } else {
                    parent.setValue(it.key, newValue)
                }
            }
        }
    }
}