package org.sdelaysam.store.visitor

import org.sdelaysam.store.store.BaseStore

internal class KeysForValueVisitor(private val value: String) : BaseVisitor() {

    val keys = mutableSetOf<String>()

    override fun visitBaseStore(store: BaseStore) {
        keys.addAll(
            store.getEntries()
                .asSequence()
                .filter { !it.value.isDeleted && it.value.value == value }
                .map { it.key }
        )
    }
}