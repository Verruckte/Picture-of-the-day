package com.example.pictureoftheday.ui.recycler_activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.pictureoftheday.databinding.RecyclerActivityBinding
import com.example.pictureoftheday.ui.recycler_activity.model.Data

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: RecyclerActivityBinding
    private lateinit var adapter: RecyclerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val data = mutableListOf(
                Pair(Data("New Note", ""), false)
        )
        data.add(0, Pair(Data("Header", ""), false))

        adapter = RecyclerActivityAdapter(
                object : OnListItemClickListener {
                    override fun onItemClick(data: Data) {
                        Toast.makeText(
                                this@RecyclerActivity,
                                data.newNote,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                data
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
        }
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
    }
}