package com.example.udemyandroidkotlin.ui.products.productAdd

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemyandroidkotlin.ApiServices.CategoryService
import com.example.udemyandroidkotlin.ApiServices.PhotoService
import com.example.udemyandroidkotlin.ApiServices.ProductService
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.models.Category
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.coroutines.launch

class ProductAddViewModel : ViewModel(), IViewModelState {
    override var loadingSate: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()

    fun getCategories(): LiveData<ArrayList<Category>> {
        loadingSate.value = LoadingState.Loading

        var categoriesReturn = MutableLiveData<ArrayList<Category>>()

        viewModelScope.launch {

            var response = CategoryService.categoryList();

            if (response.isSuccessful) {
                categoriesReturn.value = response.success
            } else {
                errorState.value = response.fail
            }

            loadingSate.value = LoadingState.Loaded


        }


        return categoriesReturn
    }


    fun addProduct(product: Product, fileUri: Uri?): LiveData<Product> {
        loadingSate.value = LoadingState.Loading

        var productReturn = MutableLiveData<Product>()


        viewModelScope.launch {

            if (fileUri != null) {
                var photoResponse = PhotoService.uploadPhoto(fileUri)

                if (photoResponse.isSuccessful) {
                    product.PhotoPath = photoResponse.success!!.Url
                } else {
                    errorState.value = photoResponse.fail

                }

            }

            var productResponse = ProductService.addProduct(product)
            if (productResponse.isSuccessful) {

                productReturn.value = productResponse.success
            } else {
                errorState.value = productResponse.fail

            }


            loadingSate.value= LoadingState.Loaded
        }




        return productReturn


    }


}