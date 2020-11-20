package com.example.udemyandroidkotlin.utility

import androidx.lifecycle.MutableLiveData
import com.example.udemyandroidkotlin.models.ApiError

interface IViewModelState {
    var loadingSate:MutableLiveData<LoadingState>
    var errorState:MutableLiveData<ApiError>
}