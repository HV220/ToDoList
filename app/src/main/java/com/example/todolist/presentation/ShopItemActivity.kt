package com.example.todolist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R

class ShopItemActivity : AppCompatActivity() {
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
        parseIntent()

        val fragment = setupFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view_shop, fragment)
            .commit()
    }

    private fun setupFragment(): ShopItemFragment {

        val fragment = when (screenMode) {
            MODE_ADD -> ShopItemFragment.getAddFragmentInstance()
            MODE_EDIT -> ShopItemFragment.getEditFragmentInstance(shopItemId)
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        return fragment
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

}