package com.example.udemyandroidkotlin.retrofitServices

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {


        fun <T> buildService(baseUrl: String, retrofitService: Class<T>, existInterceptor: Boolean): T {


            var clientBuilder = OkHttpClient.Builder();

            if (existInterceptor) {
                // interceptor eklenecek
            }

            return Retrofit.Builder().baseUrl(baseUrl).client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create()).build().create(retrofitService)


        }


    }

}