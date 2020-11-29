package com.example.udemyandroidkotlin.retrofitServices

import com.example.udemyandroidkotlin.models.Category
import com.example.udemyandroidkotlin.models.ODataModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitCategoryService {


    @GET("/odata/categories")
    suspend fun  categories():Response<ODataModel<Category>>
}