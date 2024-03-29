package com.example.todolist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todolist.R
import com.example.todolist.domain.ShopItem

class ShopItemAdapter :
    ListAdapter<ShopItem, ShopItemAdapter.ShopItemViewHolder>(ShopItemDiffCallback()) {
    companion object {
        const val ENABLED_ITEM = 1
        const val DISABLED_ITEM = 0
        const val POOL_VIEW_HOLDER = 15
        const val TAG = "ShopItemAdapter"
    }

    var onLongClickListenerShopItem: ((ShopItem) -> Unit)? = null
    var onClickListenerShopItem: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val binding = when (viewType) {
            ENABLED_ITEM -> R.layout.item_shop_enabled
            DISABLED_ITEM -> R.layout.item_shop_disabled
            else -> throw RuntimeException("layout wasn't find")
        }

        val view = LayoutInflater.from(parent.context).inflate(binding, parent, false)

        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {

        val shopItem = this.getItem(position)

        with(holder) {
            textDescriptionDo.text = shopItem.name
            textCountDo.text = shopItem.count.toString()

            itemView.setOnLongClickListener {
                onLongClickListenerShopItem?.invoke(shopItem)
                true
            }

            itemView.setOnClickListener { onClickListenerShopItem?.invoke(shopItem) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = this.getItem(position)
        return if (item.enable) ENABLED_ITEM else DISABLED_ITEM
    }

    class ShopItemViewHolder(view: View) :
        ViewHolder(view) {
        var textDescriptionDo: TextView = view.findViewById(R.id.text_description_do)
        var textCountDo: TextView = view.findViewById(R.id.text_count_do)
    }
}