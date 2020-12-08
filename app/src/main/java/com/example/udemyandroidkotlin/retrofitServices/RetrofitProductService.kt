package com.example.udemyandroidkotlin.retrofitServices

import com.example.udemyandroidkotlin.models.ODataModel
import com.example.udemyandroidkotlin.models.Product
import retrofit2.Response
import retrofit2.http.*

interface RetrofitProductService {


    //  /odata/products?$expand=category($select=Name)&select=id,name,stock,price,photoPath&$orderby id desc&$skip=10
    @GET("/odata/products?\$expand=category(\$select=Name)&\$select=id,name,stock,price,photoPath&\$orderby=id desc")
    suspend fun products(@Query("\$skip") page: Int): Response<ODataModel<Product>>


    @GET("/odata/products({productId})?\$expand=category")
    suspend fun getProduct(@Path("productId") productId: Int): Response<ODataModel<Product>>

    @POST("/odata/products")
    suspend fun addProduct(@Body product: Product): Response<Product>


    @PUT("/odata/products({productId})")
    suspend fun updateProduct(
        @Body product: Product,
        @Path("productId") productId: Int
    ): Response<Unit>


    @DELETE("/odata/products({productId})")
    suspend fun deleteProduct(@Path("productId") productId: Int): Response<Unit>

}