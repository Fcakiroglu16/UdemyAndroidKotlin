package com.example.udemyandroidkotlin.ui.signup

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.models.UserSignUp
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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



        viewModel.loadingSate.observe(viewLifecycleOwner, {

            when (it) {
                LoadingState.Loading -> fragmentView.btn_signup.startAnimation()
                LoadingState.Loaded -> fragmentView.btn_signup.revertAnimation()
            }

        })


        fragmentView.btn_signup.setOnClickListener {

            var userSignUp = UserSignUp(
                text_signup_username.editText?.text.toString(),
                text_signup_email.editText?.text.toString(),
                text_signup_password.editText?.text.toString(),
                text_signup_city.editText?.text.toString()
            )

            viewModel.signUp(userSignUp).observe(viewLifecycleOwner, {


                if (it) {

                    viewPagerLogin.currentItem = 0

                    CoroutineScope(Dispatchers.Main).launch {

                        delay(1000)
                        onAlertDailog(fragmentView)
                    }




                } else {
                    //hata var
                }

            })


        }






        return fragmentView
    }


    private fun onAlertDailog(view: View) {
        var builder = AlertDialog.Builder(view.context)

        builder.setMessage("Bilgileriniz başarıyla kaydedilmiştir.Email ve şifreniz ile giriş yapabilirsiniz")

        builder.setPositiveButton("Tamam") { _, _ -> }

        builder.show()

    }


}