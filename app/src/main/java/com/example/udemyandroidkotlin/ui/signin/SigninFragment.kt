package com.example.udemyandroidkotlin.ui.signin

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.models.UserSignIn
import com.example.udemyandroidkotlin.ui.user.UserActivity
import com.example.udemyandroidkotlin.utility.HelperService
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.android.synthetic.main.signin_fragment.view.*
import kotlinx.android.synthetic.main.signup_fragment.view.*

class SigninFragment : Fragment() {

    companion object {
        fun newInstance() = SigninFragment()
    }

    private lateinit var viewModel: SigninViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SigninViewModel::class.java)
        var root = inflater.inflate(R.layout.signin_fragment, container, false)


        viewModel.loadingSate.observe(viewLifecycleOwner,{

            when (it) {
                LoadingState.Loading -> root.btn_signin.startAnimation()
                LoadingState.Loaded -> root.btn_signin.revertAnimation()
            }

        })

        viewModel.errorState.observe(viewLifecycleOwner, {

            HelperService.showErrorMessageByToast(it)

        })


        root.btn_signin.setOnClickListener {

            val userSignIn = UserSignIn(
                root.text_signin_email.editText?.text.toString(),
                root.text_signin_password.editText?.text.toString()
            )
            viewModel.signIn(userSignIn).observe(viewLifecycleOwner, {

                if (it) {
                    var intent = Intent(requireActivity(), UserActivity::class.java)

                    startActivity(intent)
                    requireActivity().finish()
                }


            })


        }



        return root
    }


}