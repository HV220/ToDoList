package com.example.todolist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopItem(shopItem: ShopItem): Boolean {
        return shopListRepository.addShopItem(shopItem)
    }
}