package org.sdelaysam.store

internal interface Visitable {
    fun accept(visitor: Visitor)
}