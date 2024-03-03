package com.example.todolist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem): Boolean {
        return shopListRepository.editShopItem(shopItem)
    }
}