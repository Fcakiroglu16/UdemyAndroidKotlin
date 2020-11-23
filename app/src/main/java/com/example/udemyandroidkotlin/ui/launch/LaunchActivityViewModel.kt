package com.example.udemyandroidkotlin.ui.launch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemyandroidkotlin.ApiServices.TokenService
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.utility.HelperService
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LaunchActivityViewModel : ViewModel(), IViewModelState {
    override var loadingSate: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()

    var isSuccessful = MutableLiveData<Boolean>()
    private fun refreshTokenCheck() {


        CoroutineScope(Dispatchers.IO).launch {

            var token = HelperService.getTokenSharedPreference()

            if (token != null) {

                var response = TokenService.refreshToken(token.RefreshToken)

                Log.i("OkHttp","Refresh Token istek yapıldı. sonuç=${response.isSuccessful}")

                if(response.isSuccessful)
                {
                    HelperService.saveTokenSharedPreference(response.success!!)

                }

                isSuccessful.postValue(response.isSuccessful)

            } else {
                isSuccessful.postValue(false)

            }
            loadingSate.postValue(LoadingState.Loaded)

        }


    }


    fun tokenCheck() {
        loadingSate.value = LoadingState.Loading
        var status = MutableLiveData<Boolean>()

        viewModelScope.launch {


            var response = TokenService.checkToken()
            status.value = response.isSuccessful




            if (response.isSuccessful) {
                loadingSate.value = LoadingState.Loaded
                isSuccessful.value = true

            } else {
                errorState.value = response.fail
                refreshTokenCheck()

            }


        }


    }


}