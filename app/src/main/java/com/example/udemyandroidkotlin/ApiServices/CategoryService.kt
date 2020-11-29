package com.example.udemyandroidkotlin.ApiServices

import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.ApiResponse
import com.example.udemyandroidkotlin.models.Category
import com.example.udemyandroidkotlin.models.ODataModel
import com.example.udemyandroidkotlin.retrofitServices.ApiClient
import com.example.udemyandroidkotlin.retrofitServices.RetrofitCategoryService
import com.example.udemyandroidkotlin.retrofitServices.RetrofitLoginService
import com.example.udemyandroidkotlin.utility.HelperService
import java.lang.Exception

class CategoryService {

    companion object {
        private var retrofitCategoryServiceInterceptor =
            ApiClient.buildService(ApiConsts.apiBaseUrl, RetrofitCategoryService::class.java, true)


        suspend fun categoryList(): ApiResponse<ArrayList<Category>> {
            try {
                var response = retrofitCategoryServiceInterceptor.categories();

                if (!response.isSuccessful) return HelperService.handleApiError(response)

                var oDataCategory = response.body() as ODataModel<Category>
                return ApiResponse(true, oDataCategory.Value)


            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }


        }


    }
}