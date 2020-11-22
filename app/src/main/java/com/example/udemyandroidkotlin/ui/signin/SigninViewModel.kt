package com.example.udemyandroidkotlin.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemyandroidkotlin.ApiServices.LoginService
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.models.UserSignIn
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.coroutines.launch

class SigninViewModel : ViewModel(), IViewModelState {
    override var loadingSate: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()


    fun signIn(userSignIn: UserSignIn): LiveData<Boolean> {
        loadingSate.value = LoadingState.Loading
        var status = MutableLiveData<Boolean>()

        viewModelScope.launch {

            var apiResponse = LoginService.signIn(userSignIn)

            if (!apiResponse.isSuccessful) {
                status.value = false
                errorState.value = apiResponse.fail
            } else {
                status.value = true
            }

            loadingSate.value= LoadingState.Loaded

        }

        return status


    }
}