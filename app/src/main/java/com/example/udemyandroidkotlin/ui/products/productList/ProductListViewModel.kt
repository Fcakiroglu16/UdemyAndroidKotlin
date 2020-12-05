package com.example.udemyandroidkotlin.ui.products.productList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemyandroidkotlin.ApiServices.ProductService
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel(), IViewModelState {
    override var loadingSate: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()
    var products = MutableLiveData<ArrayList<Product>>()


    fun getProducts(page: Int) {

        if (page == 0) {
            loadingSate.value = LoadingState.Loading
        }

        viewModelScope.launch {

            var response = ProductService.productList(page)


            if (response.isSuccessful) {
                products.value = response.success!!
            } else {
                errorState.value = response.fail
            }

            if (page == 0) {
                loadingSate.value = LoadingState.Loaded
            }


        }


    }


}