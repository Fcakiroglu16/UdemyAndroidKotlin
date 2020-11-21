package com.example.udemyandroidkotlin.ui.signup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.models.UserSignUp
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.view.*

class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var viewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        var fragmentView = inflater.inflate(R.layout.signup_fragment, container, false)
        var viewPagerLogin = requireActivity().findViewById<ViewPager2>(R.id.ViewPagerLogin)

        fragmentView.btn_signin.setOnClickListener {

            var userSignUp = UserSignUp(
                text_signup_username.editText?.text.toString(),
                text_signup_email.editText?.text.toString(),
                text_signup_password.editText?.text.toString(),
                text_signup_city.editText?.text.toString()
            )

            viewModel.signUp(userSignUp).observe(viewLifecycleOwner, {


                if (it) {

                    viewPagerLogin.currentItem=0

                } else {
                    //hata var
                }

            })


        }






        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

}