package com.example.udemyandroidkotlin.ApiServices

import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.ApiResponse
import com.example.udemyandroidkotlin.models.UserSignUp
import com.example.udemyandroidkotlin.retrofitServices.ApiClient
import com.example.udemyandroidkotlin.retrofitServices.RetrofitLoginService
import com.example.udemyandroidkotlin.retrofitServices.RetrofitTokenService

class LoginService {

    companion object {
        private var retrofitTokenServiceWithoutInterceptor =
            ApiClient.buildService(ApiConsts.authBaseUrl, RetrofitLoginService::class.java, false)


        suspend fun signUp(userSignUp: UserSignUp): ApiResponse<Unit> {
            var tokenResponse = TokenService.getTokenWithClientCredentials();

            if (!tokenResponse.isSuccessful) return ApiResponse(false)


            var token = tokenResponse.success!!

            var signUpResponse = retrofitTokenServiceWithoutInterceptor.signUp(userSignUp,"bearer ${token.AccessToken} ")


            if(!signUpResponse.isSuccessful) return  ApiResponse(false)


            return ApiResponse(true)


        }


    }

}