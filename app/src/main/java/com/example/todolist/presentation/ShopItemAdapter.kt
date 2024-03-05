package com.example.todolist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todolist.R
import com.example.todolist.domain.ShopItem

class ShopItemAdapter() :
    RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder>() {
    companion object {
        const val ENABLED_ITEM = 1
        const val DISABLED_ITEM = 0
        const val POOL_VIEW_HOLDER = 15
    }
var count = 0
    var shopItems: List<ShopItem> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("ShopItemAdapter", "${++count}")
        val binding = when (viewType) {
            ENABLED_ITEM -> R.layout.item_shop_enabled
            DISABLED_ITEM -> R.layout.item_shop_disabled
            else -> throw RuntimeException("layout wasn't find")
        }

        val view = LayoutInflater.from(parent.context).inflate(binding, parent, false)

        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopItems[position]
        holder.textDescriptionDo.text = shopItem.name
        holder.textCountDo.text = shopItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopItems[position]
        return if (item.enable) ENABLED_ITEM else DISABLED_ITEM
    }

    override fun getItemCount(): Int = shopItems.size

    class ShopItemViewHolder(view: View) :
        ViewHolder(view) {
        var textDescriptionDo = view.findViewById<TextView>(R.id.text_description_do)
        var textCountDo = view.findViewById<TextView>(R.id.text_count_do)
    }
}