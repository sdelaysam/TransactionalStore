package org.sdelaysam.store

interface Visitor {
    fun visit(store: Store)
}