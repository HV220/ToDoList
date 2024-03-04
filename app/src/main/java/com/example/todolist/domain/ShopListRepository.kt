package com.example.todolist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem): Boolean
    fun deleteShopItem(shopItem: ShopItem): Boolean
    fun editShopItem(shopItem: ShopItem): Boolean
    fun getShopItem(id: Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
}