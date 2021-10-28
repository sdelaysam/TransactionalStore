package org.sdelaysam.store.query

import org.sdelaysam.store.Store
import org.sdelaysam.store.StoreQueries
import org.sdelaysam.store.visitor.FilterByValueVisitor

internal class LinearStoreQueries(
    private val stores: Iterable<Store>
) : StoreQueries {

    override fun countKeysByValue(value: String): Int {
        val visitor = FilterByValueVisitor(value = value)
        stores.forEach { visitor.visit(it) }
        return visitor.count()
    }
}