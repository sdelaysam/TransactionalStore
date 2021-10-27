package org.sdelaysam.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val store = TransactionalStore()
        store.getValue("1")
        store.beginTransaction()
        store.setValue("1", "1")
    }
}