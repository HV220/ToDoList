package com.example.todolist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R

class MainActivity : AppCompatActivity() {
    private lateinit var model: MainViewModel
    private lateinit var llShopList: LinearLayout

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProvider(this)[MainViewModel::class.java]
        llShopList = findViewById(R.id.list_item)

        loadList()
    }

    private fun loadList() {
        model.getShopList().observe(this) {
            llShopList.removeAllViews()
            for (item in it) {
                var idLayoutShop = R.layout.item_shop_disabled

                if (item.enable) idLayoutShop = R.layout.item_shop_enabled

                val view =
                    LayoutInflater.from(this@MainActivity)
                        .inflate(idLayoutShop, null, false)

                val textDescriptionDo = view.findViewById<TextView>(R.id.text_description_do)
                val textCountDo = view.findViewById<TextView>(R.id.text_count_do)

                textDescriptionDo.text = item.name
                textCountDo.text = item.count.toString()

                view.setOnLongClickListener {
                    model.editEnableStateShopItem(item)
                    true
                }
                llShopList.addView(view)
            }
        }
    }
}