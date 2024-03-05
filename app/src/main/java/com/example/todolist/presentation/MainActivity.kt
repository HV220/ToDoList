package com.example.todolist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class MainActivity : AppCompatActivity() {
    private lateinit var model: MainViewModel
    private lateinit var adapter: ShopItemAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupRecyclerView()
        setupAdapter()
        setupViewModelClickListener()
    }

    private fun setupAdapter() {
        adapter = ShopItemAdapter()
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        model = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.toDoListRecyclerView)
    }

    private fun setupViewModelClickListener() {
        model.getShopList().observe(this) {
            adapter.shopItems = it
        }
    }
}
