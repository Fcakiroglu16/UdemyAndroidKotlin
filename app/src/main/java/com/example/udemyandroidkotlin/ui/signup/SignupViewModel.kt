package com.example.udemyandroidkotlin.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemyandroidkotlin.ApiServices.LoginService
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.models.UserSignUp
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel(), IViewModelState {
    override var loadingSate: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()


    fun signUp(userSignUp: UserSignUp): LiveData<Boolean> {
        var status = MutableLiveData<Boolean>()
        loadingSate.value = LoadingState.Loading

        viewModelScope.launch {

            var response = LoginService.signUp(userSignUp)

            status.value = response.isSuccessful
            loadingSate.value = LoadingState.Loaded

            if (!response.isSuccessful) errorState.value = response.fail

        }
        return status

    }
}