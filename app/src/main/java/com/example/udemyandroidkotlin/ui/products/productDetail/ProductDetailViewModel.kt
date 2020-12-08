package com.example.udemyandroidkotlin.ui.products.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemyandroidkotlin.ApiServices.ProductService
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel(), IViewModelState {
    override var loadingSate: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()


    fun getProduct(productId: Int): LiveData<Product> {
        var productReturn = MutableLiveData<Product>()

        loadingSate.value = LoadingState.Loading

        viewModelScope.launch {

            var response = ProductService.getProductById(productId)

            if (response.isSuccessful) {
                productReturn.value = response.success

            } else {
                errorState.value = response.fail
            }
            loadingSate.value = LoadingState.Loaded

        }
        return productReturn

    }

    fun deleteProduct(productId: Int): LiveData<Boolean> {
        var productReturn = MutableLiveData<Boolean>()

        loadingSate.value = LoadingState.Loading

        viewModelScope.launch {

            var response = ProductService.deleteProduct(productId)

            if (response.isSuccessful) {
                productReturn.value = true

            } else {
                errorState.value = response.fail
            }
            loadingSate.value = LoadingState.Loaded

        }
        return productReturn

    }
}