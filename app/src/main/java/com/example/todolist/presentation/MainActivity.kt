package com.example.todolist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class MainActivity : AppCompatActivity() {
    private lateinit var model: MainViewModel
    private lateinit var adapter: ShopItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var touchShopItem: ItemTouchHelper
    private lateinit var swipeDeleteShopItem: ItemTouchHelper.SimpleCallback

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
        setupAdapterOnClickListener()
        setupSwipeDeleteShopItem()
        setTouchHelperToRecyclerView()
    }

    private fun setupAdapter() {
        adapter = ShopItemAdapter()
        recyclerView.adapter = adapter

        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopItemAdapter.ENABLED_ITEM,
            ShopItemAdapter.POOL_VIEW_HOLDER
        )

        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopItemAdapter.DISABLED_ITEM,
            ShopItemAdapter.POOL_VIEW_HOLDER
        )
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

    private fun setupAdapterOnClickListener() {
        adapter.onClickListenerShopItem = {
            Log.d(TAG, it.toString())
        }
        adapter.onLongClickListenerShopItem = {
            model.editEnableStateShopItem(it)
            Log.d(TAG, it.toString())
        }
    }

    private fun setupSwipeDeleteShopItem() {
        swipeDeleteShopItem =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    model.deleteShopItem(adapter.shopItems[viewHolder.adapterPosition])
                }
            }
    }

    private fun setTouchHelperToRecyclerView() {
        touchShopItem = ItemTouchHelper(swipeDeleteShopItem)
        touchShopItem.attachToRecyclerView(recyclerView)
    }
}
