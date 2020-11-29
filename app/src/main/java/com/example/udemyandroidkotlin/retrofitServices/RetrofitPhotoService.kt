package com.example.udemyandroidkotlin.retrofitServices

import com.example.udemyandroidkotlin.models.Photo
import com.example.udemyandroidkotlin.models.PhotoDelete
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitPhotoService {

    @Multipart
    @POST("/api/photos")
    suspend fun  upload(@Part photo:MultipartBody.Part): Response<Photo>

    @HTTP(method = "DELETE", path = "/api/photos",hasBody = true)
    suspend fun  delete(@Body photoDelete:PhotoDelete):Response<Unit>

}