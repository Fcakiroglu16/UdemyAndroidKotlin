package com.example.udemyandroidkotlin.ApiServices

import android.net.Uri
import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.ApiResponse
import com.example.udemyandroidkotlin.models.Photo
import com.example.udemyandroidkotlin.models.PhotoDelete
import com.example.udemyandroidkotlin.retrofitServices.ApiClient
import com.example.udemyandroidkotlin.retrofitServices.RetrofitLoginService
import com.example.udemyandroidkotlin.retrofitServices.RetrofitPhotoService
import com.example.udemyandroidkotlin.utility.GlobalApp
import com.example.udemyandroidkotlin.utility.HelperService
import com.example.udemyandroidkotlin.utility.RealPathUtil
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.lang.Exception

class PhotoService {

    companion object {

        private var retrofitPhotoServiceInterceptor =
            ApiClient.buildService(ApiConsts.photoBaseUrl, RetrofitPhotoService::class.java, false)


        suspend fun uploadPhoto(fileUri: Uri): ApiResponse<Photo> {

            try {
                var path = RealPathUtil.getRealPath(GlobalApp.getAppContext(), fileUri)

                var file = File(path)


                var requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

                var body = MultipartBody.Part.createFormData("photo", file.name, requestFile)

                var response = retrofitPhotoServiceInterceptor.upload(body)


                if (!response.isSuccessful) return HelperService.handleApiError(response)


                return ApiResponse(true, response.body())
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }


        }


        suspend fun deletePhoto(photoDelete: PhotoDelete): ApiResponse<Unit> {


            try {

                var response = retrofitPhotoServiceInterceptor.delete(photoDelete)

                if (!response.isSuccessful) return HelperService.handleApiError(response)


                return ApiResponse(true)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }

    }
}