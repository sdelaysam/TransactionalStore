package org.sdelaysam.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val store = Stores.linear()
        store.getValue("1")
        store.beginTransaction()
        store.setValue("1", "1")
    }
}
