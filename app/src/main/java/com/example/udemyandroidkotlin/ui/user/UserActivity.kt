package com.example.udemyandroidkotlin.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.utility.HelperService
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {


    companion object {
        lateinit var loadingView: View

        fun setLoadingStatus(viewModel: IViewModelState, viewLifecycleOwner: LifecycleOwner) {
            viewModel.loadingSate.observe(viewLifecycleOwner, {

                when (it) {
                    LoadingState.Loading -> loadingView.visibility = View.VISIBLE
                    LoadingState.Loaded -> loadingView.visibility = View.GONE
                }


            })

        }

        fun setErrorStatus(viewModel: IViewModelState, viewLifecycleOwner: LifecycleOwner) {


            viewModel.errorState.observe(viewLifecycleOwner, {

                HelperService.showErrorMessageByToast(it)


            })
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        loadingView = full_page_loading_view

    }
}