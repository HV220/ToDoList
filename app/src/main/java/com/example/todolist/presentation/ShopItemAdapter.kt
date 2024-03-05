package com.example.todolist.presentation

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

    var shopItems: List<ShopItem> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val binding = if (viewType == 1) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enabled, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled, parent, false)
        }
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopItems[position]
        holder.textDescriptionDo.text = shopItem.name
        holder.textCountDo.text = shopItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopItems[position]
        return if (item.enable) 1 else 0
    }

    override fun getItemCount(): Int = shopItems.size

    class ShopItemViewHolder(view: View) :
        ViewHolder(view) {
        var textDescriptionDo = view.findViewById<TextView>(R.id.text_description_do)
        var textCountDo = view.findViewById<TextView>(R.id.text_count_do)
    }
}