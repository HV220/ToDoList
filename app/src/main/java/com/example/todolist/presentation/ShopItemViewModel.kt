package com.example.todolist.presentation

import androidx.lifecycle.ViewModel
import com.example.todolist.data.ShopListRepositoryImpl
import com.example.todolist.domain.AddShopItemUseCase
import com.example.todolist.domain.EditShopItemUseCase
import com.example.todolist.domain.GetShopItemUseCase
import com.example.todolist.domain.ShopItem

class ShopItemViewModel : ViewModel() {
    companion object {

        const val ERROR_PARSE_INPUT_COUNT = 0
    }

    private val shopListRepositoryImpl: ShopListRepositoryImpl = ShopListRepositoryImpl()
    private val addShopItemUseCase: AddShopItemUseCase = AddShopItemUseCase(shopListRepositoryImpl)
    private val editShopItemUseCase: EditShopItemUseCase =
        EditShopItemUseCase(shopListRepositoryImpl)
    private val getShopItemUseCase: GetShopItemUseCase =
        GetShopItemUseCase(shopListRepositoryImpl)

    fun addShopItemToMainList(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val value = parseInputCount(inputCount)

        if (checkCorrectInputValues(name, value)) {
            val result = ShopItem(name = name, count = value, enable = true)
            addShopItemUseCase.addShopItem(result)
        }
    }

    fun editShopItemFromMainList(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val value = parseInputCount(inputCount)

        if (checkCorrectInputValues(name, value)) {
            val result = ShopItem(name = name, count = value, enable = true)
            editShopItemUseCase.editShopItem(result)
        }
    }


    fun getShopItemById(id: Int): ShopItem {
        return getShopItemUseCase.getShopItem(id)
    }

    private fun parseInputName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseInputCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: ERROR_PARSE_INPUT_COUNT
        } catch (e: Exception) {
            ERROR_PARSE_INPUT_COUNT
        }
    }

    private fun checkCorrectInputValues(name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            //TODO: show error input name
            result = false
        }

        if (count > ERROR_PARSE_INPUT_COUNT) {
            //TODO: show error input count
            result = false
        }

        return result

    }
}