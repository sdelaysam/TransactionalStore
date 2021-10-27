package org.sdelaysam.store

import org.sdelaysam.store.visitor.CommitTransactionVisitor
import org.sdelaysam.store.visitor.KeysForValueVisitor
import java.util.*
import kotlin.collections.HashMap

// Keeps the base data and controls transaction list.
// Not thread-safe, only allows single branch of transactions.
internal class TransactionalStoreImpl : TransactionalStore, Visitable {

    internal val keyValues = HashMap<String, String>()

    // could mark it @VisibleForTesting but don't want neither guava nor android dependencies here
    internal val transactions = LinkedList<Transaction>()

    override fun setValue(key: String, value: String) {
        keyValues[key] = value
    }

    override fun getValue(key: String): String? {
        return keyValues[key]
    }

    override fun deleteKey(key: String) {
        keyValues.remove(key)
    }

    override fun countValues(value: String): Int {
        val visitor = KeysForValueVisitor(value)
        accept(visitor)
        transactions.forEach { it.accept(visitor) }
        return visitor.keys.size
    }

    override fun beginTransaction() {
        val parentStore = transactions.lastOrNull() ?: this
        val transaction = Transaction(parentStore = parentStore)
        transactions.add(transaction)
    }

    override fun commitTransaction(): Boolean {
        return transactions.lastOrNull()
            ?.accept(CommitTransactionVisitor()) != null
    }

    override fun rollbackTransaction(): Boolean {
        return transactions.removeLastOrNull() != null
    }

    override fun accept(visitor: Visitor) {
        visitor.visitStore(this)
    }
}