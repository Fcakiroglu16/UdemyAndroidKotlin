package com.example.udemyandroidkotlin.ui.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider


import com.example.udemyandroidkotlin.R

class LaunchActivity : AppCompatActivity() {
    lateinit var viewModel: LaunchActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        viewModel = ViewModelProvider(this).get(LaunchActivityViewModel::class.java)


    }
}