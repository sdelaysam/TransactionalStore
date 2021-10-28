package org.sdelysam.store

import org.junit.Assert.*
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

    @Test
    fun `commit transaction`() {
        val (key, value) = "foo" to "456"
        val store = Stores.linear()
        store.beginTransaction()
        store.setValue(key, value)
        assertEquals(value, store.getValue(key))
        assertTrue(store.commitTransaction())
        assertEquals(value, store.getValue(key))
        assertFalse(store.rollbackTransaction())
    }

    @Test
    fun `rollback transaction`() {
        val (key1, value1) = "foo" to "123"
        val (key2, value2) = "bar" to "abc"
        val value3 = "456"
        val value4 = "def"
        val store = Stores.linear()
        store.setValue(key1, value1)
        store.setValue(key2, value2)
        store.beginTransaction()
        store.setValue(key1, value3)
        assertEquals(value3, store.getValue(key1))
        store.setValue(key2, value4)
        assertEquals(value4, store.getValue(key2))
        assertTrue(store.rollbackTransaction())
        assertEquals(value1, store.getValue(key1))
        assertEquals(value2, store.getValue(key2))
        assertFalse(store.rollbackTransaction())
    }

    @Test
    fun `nested transactions`() {
        val (key1, value1) = "foo" to "123"
        val value2 = "456"
        val value3 = "789"
        val store = Stores.linear()
        store.setValue(key1, value1)
        store.beginTransaction()
        store.setValue(key1, value2)
        store.beginTransaction()
        store.setValue(key1, value3)
        assertTrue(store.rollbackTransaction())
        assertEquals(value2, store.getValue(key1))
        assertTrue(store.rollbackTransaction())
        assertEquals(value1, store.getValue(key1))
    }

    @Test
    fun `nested set delete`() {
        val (key1, value1) = "foo" to "123"
        val value2 = "456"
        val store = Stores.linear()
        store.setValue(key1, value1)
        store.beginTransaction()
        store.deleteKey(key1)
        store.beginTransaction()
        store.setValue(key1, value2)
        assertEquals(value2, store.getValue(key1))
        assertTrue(store.rollbackTransaction())
        assertNull(store.getValue(key1))
        assertTrue(store.rollbackTransaction())
        assertEquals(value1, store.getValue(key1))
        assertFalse(store.rollbackTransaction())
    }

    @Test
    fun `count values in nested transactions`() {
        val (key1, value1) = "foo" to "123"
        val key2 = "bar"
        val value2 = "456"
        val store = Stores.linear()
        store.setValue(key1, value1)
        store.setValue(key2, value1)
        store.beginTransaction()
        store.deleteKey(key2)
        store.beginTransaction()
        store.setValue(key1, value2)
        store.beginTransaction()
        store.setValue(key1, value1)
        store.setValue(key2, value1)
        assertEquals(2, store.queries.countKeysByValue(value1))
        assertEquals(0, store.queries.countKeysByValue(value2))
        assertTrue(store.rollbackTransaction())
        assertEquals(0, store.queries.countKeysByValue(value1))
        assertEquals(1, store.queries.countKeysByValue(value2))
        assertTrue(store.rollbackTransaction())
        assertEquals(1, store.queries.countKeysByValue(value1))
        assertEquals(0, store.queries.countKeysByValue(value2))
        assertTrue(store.rollbackTransaction())
        assertEquals(2, store.queries.countKeysByValue(value1))
        assertEquals(0, store.queries.countKeysByValue(value2))
        assertFalse(store.rollbackTransaction())
    }

    @Test
    fun `in transaction getter`() {
        val store = Stores.linear()
        assertFalse(store.inTransaction)
        store.beginTransaction()
        assertTrue(store.inTransaction)
        store.beginTransaction()
        assertTrue(store.inTransaction)
        assertTrue(store.commitTransaction())
        assertTrue(store.inTransaction)
        assertTrue(store.rollbackTransaction())
        assertFalse(store.inTransaction)
        assertFalse(store.rollbackTransaction())
        assertFalse(store.inTransaction)
    }
}