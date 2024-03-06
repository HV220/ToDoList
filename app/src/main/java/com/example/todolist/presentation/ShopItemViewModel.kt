package com.example.todolist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.ShopListRepositoryImpl
import com.example.todolist.domain.AddShopItemUseCase
import com.example.todolist.domain.EditShopItemUseCase
import com.example.todolist.domain.GetShopItemUseCase
import com.example.todolist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean> get() = _errorInputCount

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit> get() = _shouldCloseScreen

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem> get() = _shopItem

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
            resetShouldCloseScreen()
        }
    }

    fun editShopItemFromMainList(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val value = parseInputCount(inputCount)

        if (checkCorrectInputValues(name, value)) {
            shopItem.value?.let {
                val item = it.copy(name = name, count = value)
                editShopItemUseCase.editShopItem(item)
                resetShouldCloseScreen()
            }

        }
    }

    fun getShopItemById(id: Int) {
        val item = getShopItemUseCase.getShopItem(id)
        _shopItem.value = item
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
            _errorInputName.value = true
            result = false
        }

        if (count > ERROR_PARSE_INPUT_COUNT) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    private fun resetErrorInputName() {
        _errorInputName.value = false
    }

    private fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun resetShouldCloseScreen() {
        _shouldCloseScreen.value = Unit
    }
}