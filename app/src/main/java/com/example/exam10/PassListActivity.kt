package com.example.exam10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PassListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_list)
        val recyclerViewPasswords = findViewById<RecyclerView>(R.id.rv_passwords)
        val passes: String = intent.getStringExtra("passwords")!!
        val passesList = passes.split('\n')
        val itemList = mutableListOf<Item>()
        for (i in 0 until passesList.size) {
            itemList.add(Item(password = passesList.get(i)))
        }
        recyclerViewPasswords.adapter = PassListAdapter(itemList)
        recyclerViewPasswords.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewPasswords.setHasFixedSize(true)

    }
}