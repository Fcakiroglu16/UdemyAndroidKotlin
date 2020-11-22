package com.example.udemyandroidkotlin.ui.launch

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.ui.login.LoginActivity
import com.example.udemyandroidkotlin.ui.user.UserActivity
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {
    lateinit var viewModel: LaunchActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        viewModel = ViewModelProvider(this).get(LaunchActivityViewModel::class.java)

        var scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f)
        var scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f)

        var animator = ObjectAnimator.ofPropertyValuesHolder(img_company_logo, scaleX, scaleY)

        animator.repeatMode = ObjectAnimator.REVERSE
        animator.repeatCount = Animation.INFINITE
        animator.duration = 1000

        viewModel.loadingSate.observe(this, {

            when (it) {
                LoadingState.Loading -> animator.start()
                LoadingState.Loaded -> animator.cancel()
            }

        })





        viewModel.tokenCheck().observe(this, {


            var intent = when (it) {
                true -> {
                    Intent(this@LaunchActivity, UserActivity::class.java)
                }
                false -> {
                    Intent(this@LaunchActivity, LoginActivity::class.java)
                }
            }
            startActivity(intent)
            finish()

        })


    }
}