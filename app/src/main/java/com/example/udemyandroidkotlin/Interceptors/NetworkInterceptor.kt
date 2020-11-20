package com.example.udemyandroidkotlin.Interceptors

import com.example.udemyandroidkotlin.Exceptions.OfflineException
import com.example.udemyandroidkotlin.utility.LiveNetworkListener
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        if(!LiveNetworkListener.isConnected())
        {
            throw OfflineException("")
        }

        var request= chain.request()

        return chain.proceed(request)



    }


}