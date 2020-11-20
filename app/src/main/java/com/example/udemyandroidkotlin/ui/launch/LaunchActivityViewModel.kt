package com.example.udemyandroidkotlin.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemyandroidkotlin.ApiServices.TokenService
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.coroutines.launch

class LaunchActivityViewModel : ViewModel(), IViewModelState {
    override var loadingSate: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()


    fun tokenCheck(): LiveData<Boolean> {
        loadingSate.value = LoadingState.Loading
        var status = MutableLiveData<Boolean>()

        viewModelScope.launch {

            var response = TokenService.checkToken()

            status.value = response.isSuccessful

            loadingSate.value = LoadingState.Loaded

            if (response.isSuccessful) {
                errorState.value = response.fail
            }
        }





        return status
    }


}