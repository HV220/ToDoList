package com.example.todolist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
    private lateinit var tilName: TextInputLayout
    private lateinit var etName: TextInputEditText
    private lateinit var tilCount: TextInputLayout
    private lateinit var etCount: TextInputEditText
    private lateinit var bSave: MaterialButton
    private lateinit var shopItemViewModel: ShopItemViewModel

    private var screenMode = UNDEFINED_SCREEN_MODE
    private var shopItemId = UNDEFINED_ID

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNDEFINED_ID = -1
        private const val UNDEFINED_SCREEN_MODE = ""
        private const val TAG = "ShopItemActivity"
        private val CLEARED_ERROR = null
        fun getEditShowItemIntent(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, itemId)
            return intent
        }

        fun getAddShowItemIntent(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        initViews()
        parseIntent()
        setupViewModel()
        setupViewModelOnClickListener()
        choseActionScreenMode()
        setubViewModelObservable()
        setupViewDoOnTextChanged()
    }

    private fun setupViewDoOnTextChanged() {
        etName.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                tilName.error = getString(R.string.folder_can_t_be_empty)
            } else {
                tilName.error = CLEARED_ERROR
            }
        }

        etCount.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                tilCount.error = getString(R.string.folder_can_t_be_empty)
            } else {
                tilCount.error = CLEARED_ERROR
            }
        }
    }

    private fun setubViewModelObservable() {
        shopItemViewModel.errorInputName.observe(this) {
            when (it) {
                true -> tilName.error = getString(R.string.there_is_some_problem_with_name)
                false -> tilName.error = null
            }
        }

        shopItemViewModel.errorInputCount.observe(this) {
            when (it) {
                true -> tilCount.error = getString(R.string.there_is_some_problem_with_number)
                false -> tilCount.error = null
            }
        }
    }

    private fun choseActionScreenMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        etName = findViewById(R.id.et_name)
        tilCount = findViewById(R.id.til_count)
        etCount = findViewById(R.id.et_count)
        bSave = findViewById(R.id.b_save)
    }

    private fun launchAddMode() {
        setupSaveButtonOnClickListener()
    }

    private fun setupSaveButtonOnClickListener() {
        bSave.setOnClickListener {
            val nameInput = etName.text.toString()
            val countInput = etCount.text.toString()

            shopItemViewModel.addShopItemToMainList(nameInput, countInput)
        }
    }

    private fun launchEditMode() {
        shopItemViewModel.getShopItemById(shopItemId)

        shopItemViewModel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        bSave.setOnClickListener {
            shopItemViewModel.editShopItemFromMainList(

                etName.text.toString(),
                etCount.text.toString()
            )
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)

        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }

    private fun setupViewModel() {
        shopItemViewModel = ViewModelProvider(this@ShopItemActivity)[ShopItemViewModel::class.java]
    }

    private fun setupViewModelOnClickListener() {
        shopItemViewModel.shouldCloseScreen.observe(this) {
            Log.d(TAG, "test")
            finish()
        }
    }
}