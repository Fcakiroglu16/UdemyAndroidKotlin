package com.example.udemyandroidkotlin.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.ui.signin.SigninFragment
import com.example.udemyandroidkotlin.ui.signup.SignupFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var pagerAdapter = ScreenSlidePagerAdapter(this)
        pagerAdapter.addFragment(SigninFragment())
        pagerAdapter.addFragment(SignupFragment())


        ViewPagerLogin.adapter = pagerAdapter




    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        var fragments = ArrayList<Fragment>()

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(f: Fragment) {

            fragments.add(f)
        }


    }


}