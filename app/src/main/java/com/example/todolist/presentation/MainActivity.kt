package com.example.todolist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R

class MainActivity : AppCompatActivity() {
    private lateinit var model: MainViewModel

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProvider(this)[MainViewModel::class.java]

        model.getShopList().observe(this) {
            Log.d(TAG, it.toString())
        }
    }
}