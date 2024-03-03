package com.example.todolist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun deleteShopItem(shopItem: ShopItem): Boolean {
        return shopListRepository.deleteShopItem(shopItem)
    }
}