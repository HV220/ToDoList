package com.example.todolist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.ShopListRepositoryImpl
import com.example.todolist.domain.DeleteShopItemUseCase
import com.example.todolist.domain.EditShopItemUseCase
import com.example.todolist.domain.GetShopListUseCase
import com.example.todolist.domain.ShopItem

class MainViewModel : ViewModel() {
    private val realisation = ShopListRepositoryImpl()

    private val getShopListUseCase = GetShopListUseCase(realisation)
    private val editShopItemUseCase = EditShopItemUseCase(realisation)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(realisation)

    fun getShopList(): LiveData<List<ShopItem>> {
        return getShopListUseCase.getShopList()
    }

    fun editEnableStateShopItem(shopItem: ShopItem) {
        val item = shopItem.copy(enable = !shopItem.enable)
        editShopItemUseCase.editShopItem(item)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }
}