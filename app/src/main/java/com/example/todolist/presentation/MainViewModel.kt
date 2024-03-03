package com.example.todolist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.ShopListRepositoryImpl
import com.example.todolist.domain.DeleteShopItemUseCase
import com.example.todolist.domain.EditShopItemUseCase
import com.example.todolist.domain.GetShopListUseCase
import com.example.todolist.domain.ShopItem

class MainViewModel : ViewModel() {
    private var showList = MutableLiveData<List<ShopItem>>()

    private val realisation = ShopListRepositoryImpl()

    private val getShopListUseCase = GetShopListUseCase(realisation)
    private val editShopItemUseCase = EditShopItemUseCase(realisation)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(realisation)

    fun getShopList() {
        val list = getShopListUseCase.getShopList()

        showList.value = list
    }

    fun editEnableStateShopItem(shopItem: ShopItem) {
        val item = shopItem.copy(enable = !shopItem.enable)
        editShopItemUseCase.editShopItem(item)
        getShopList()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopList()
    }
}