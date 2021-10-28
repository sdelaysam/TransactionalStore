package org.sdelaysam.store.visitor

import org.sdelaysam.store.store.BaseStore
import org.sdelaysam.store.store.StoreItem

internal class FilterByValueVisitor(private val value: String) : BaseVisitor() {

    private val map = mutableMapOf<String, StoreItem>()

    override fun visitBaseStore(store: BaseStore) {
        store.getEntries().forEach {
            val item = it.value
            if (item.oldValue == value || item.newValue == value) {
                map[it.key] = item
            }
        }
    }

    fun count(): Int {
        return map.count { it.value.newValue == value }
    }
}