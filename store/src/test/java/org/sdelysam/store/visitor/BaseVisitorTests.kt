package org.sdelysam.store.visitor

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.sdelaysam.store.Store
import org.sdelaysam.store.store.BaseStore
import org.sdelaysam.store.store.StoreItem
import org.sdelaysam.store.visitor.BaseVisitor

class BaseVisitorTests {

    @Test(expected = IllegalArgumentException::class)
    fun `throws on unknown store`() {
        val store = mock<Store>()
        TestVisitor().visit(store)
    }

    @Test
    fun `visits expected store`() {
        val store = spy(TestStore())
        TestVisitor().visit(store)
        verify(store).deleteKey(TestVisitor.KEY)
    }

    private open class TestStore : BaseStore(null) {
        override fun setItem(key: String, entry: StoreItem) {
        }

        override fun getItem(key: String): StoreItem? {
            return null
        }

        override fun getEntries(): Iterable<Map.Entry<String, StoreItem>> {
            return emptyList()
        }
    }

    private class TestVisitor : BaseVisitor() {
        companion object {
            const val KEY = "123"
        }
        override fun visitBaseStore(store: BaseStore) {
            store.deleteKey(KEY)
        }
    }

}