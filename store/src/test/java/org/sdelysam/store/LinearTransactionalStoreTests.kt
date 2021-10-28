package org.sdelysam.store

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.sdelaysam.store.Stores

class LinearTransactionalStoreTests {

    @Test
    fun `set and get value`() {
        val (key, value) = "foo" to "123"
        val store = Stores.linear()
        store.setValue(key, value)
        assertEquals(value, store.getValue(key))
    }

    @Test
    fun `delete value`() {
        val (key, value) = "foo" to "123"
        val store = Stores.linear()
        store.setValue(key, value)
        store.deleteKey(key)
        assertNull(store.getValue(key))
    }

    @Test
    fun `count values`() {
        val (key1, value1) = "foo" to "123"
        val (key2, value2) = "bar" to "456"
        val key3 = "baz"
        val store = Stores.linear()
        store.setValue(key1, value1)
        store.setValue(key2, value2)
        store.setValue(key3, value1)
        assertEquals(2, store.queries.countKeysByValue(value1))
        assertEquals(1, store.queries.countKeysByValue(value2))
        assertEquals(0, store.queries.countKeysByValue(""))
    }

}