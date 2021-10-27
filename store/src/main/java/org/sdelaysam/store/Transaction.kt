package org.sdelaysam.store

// Transaction modifications are kept here.
internal class Transaction(internal val parentStore: Store) : Store, Visitable {

    internal val modifications = HashMap<String, String>()

    internal val deletedKeys = HashSet<String>()

    override fun setValue(key: String, value: String) {
        deletedKeys.remove(key)
        if (parentStore.getValue(key) != value) {
            modifications[key] = value
        }
    }

    override fun getValue(key: String): String? {
        return if (!deletedKeys.contains(key)) {
            modifications[key] ?: parentStore.getValue(key)
        } else {
            null
        }
    }

    override fun deleteKey(key: String) {
        deletedKeys.add(key)
    }

    override fun accept(visitor: Visitor) {
        visitor.visitTransaction(this)
    }
}