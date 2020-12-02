package com.example.udemyandroidkotlin.Interceptors

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.udemyandroidkotlin.ApiServices.TokenService
import com.example.udemyandroidkotlin.models.TokenAPI
import com.example.udemyandroidkotlin.ui.login.LoginActivity
import com.example.udemyandroidkotlin.utility.GlobalApp
import com.example.udemyandroidkotlin.utility.HelperService
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

// api/product/save
        var token: TokenAPI? = null

        var request = chain.request()

        var preference =
            GlobalApp.getAppContext().getSharedPreferences("token_api", Context.MODE_PRIVATE)

        var tokenString = preference.getString("token", null)

        if (tokenString != null) {
            Log.i("OkHttp", "token  sharedpreference'den okundu")
            token = Gson().fromJson(tokenString, TokenAPI::class.java)

            request = request.newBuilder().addHeader("Authorization", "Bearer ${token.AccessToken}")
                .build()

        }
        var response = chain.proceed(request)

        if (response.code == 401) {
            Log.i("OkHttp", "AccessToken geçersiz 401'girdi")

            if (token != null) {
                var apiResponse = TokenService.refreshToken(token.RefreshToken)


                if (apiResponse.isSuccessful) {
                    HelperService.saveTokenSharedPreference(apiResponse.success!!)

                    var newToken = apiResponse.success!!

                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer ${newToken.AccessToken}")
                        .build()


                    response = chain.proceed(request)


                } else {

                    var intent= Intent(GlobalApp.getAppContext(),LoginActivity::class.java)

                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK

                    GlobalApp.getAppContext().startActivity(intent)

                }

            }


        }

        return response
    }


}