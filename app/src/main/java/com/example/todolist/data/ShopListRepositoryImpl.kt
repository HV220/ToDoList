package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.domain.ShopItem
import com.example.todolist.domain.ShopListRepository
import kotlin.random.Random

class ShopListRepositoryImpl : ShopListRepository {

    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()

    private var generatorId = 0

    init {
        for (i in 0..100) {
            addShopItem(ShopItem("name $i", i, Random.nextBoolean()))
        }
    }

    override fun addShopItem(shopItem: ShopItem): Boolean = try {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = generatorId++
        }
        shopList.add(shopItem)
        updateListLiveData()
        true
    } catch (e: Exception) {
        false
    }

    override fun deleteShopItem(shopItem: ShopItem): Boolean = try {
        shopList.remove(shopItem)
        updateListLiveData()
        true
    } catch (e: Exception) {
        false
    }

    override fun editShopItem(shopItem: ShopItem): Boolean = try {
        val previousItem = getShopItem(shopItem.id)

        shopList.remove(previousItem)
        addShopItem(shopItem)
        true
    } catch (e: Exception) {
        false
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    private fun updateListLiveData() {
        shopListLiveData.value = shopList.toList()
    }
}