package com.example.udemyandroidkotlin.ui.products.productUpdate

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
import com.example.udemyandroidkotlin.models.PhotoDelete
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.utility.IViewModelState
import com.example.udemyandroidkotlin.utility.LoadingState
import kotlinx.coroutines.launch

class ProductUpdateViewModel : ViewModel(), IViewModelState {
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

    fun updateProduct(product: Product, fileUri: Uri?): LiveData<Boolean> {
        loadingSate.value = LoadingState.Loading

        var statusReturn = MutableLiveData<Boolean>()

        viewModelScope.launch {
            if (fileUri != null) {
                if (!product.PhotoPath.isNullOrEmpty()) {
                    PhotoService.deletePhoto(PhotoDelete(product.PhotoPath))
                }


                var photoResponse = PhotoService.uploadPhoto(fileUri)

                if (photoResponse.isSuccessful) {
                    product.PhotoPath = photoResponse.success!!.Url
                } else {
                    errorState.value = photoResponse.fail

                }

            }


            var response = ProductService.updateProduct(product)

            if (response.isSuccessful) {
                statusReturn.value = true
            } else {
                errorState.value = response.fail
            }


        }





        return statusReturn


    }


}