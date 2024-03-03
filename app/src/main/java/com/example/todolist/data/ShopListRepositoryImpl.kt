package com.example.todolist.data

import com.example.todolist.domain.ShopItem
import com.example.todolist.domain.ShopListRepository

class ShopListRepositoryImpl : ShopListRepository {

    private val shopList = arrayListOf<ShopItem>()
    private var generatorId = 0

    override fun addShopItem(shopItem: ShopItem): Boolean = try {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = generatorId++
        }
        shopList.add(shopItem)
        true
    } catch (e: Exception) {
        false
    }

    override fun deleteShopItem(shopItem: ShopItem): Boolean = try {
        shopList.remove(shopItem)
        true
    } catch (e: Exception) {
        false
    }

    override fun editShopItem(shopItem: ShopItem): Boolean = try {
        val previousItem = getShopItem(shopItem.id)

        shopList.remove(previousItem)
        shopList.add(shopItem)
    } catch (e: Exception) {
        false
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException()
    }


    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}