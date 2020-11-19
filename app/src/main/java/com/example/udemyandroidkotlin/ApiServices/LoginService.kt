package com.example.udemyandroidkotlin.ApiServices

import com.example.udemyandroidkotlin.BuildConfig
import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.ApiResponse
import com.example.udemyandroidkotlin.models.Token
import com.example.udemyandroidkotlin.models.UserSignIn
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

            var signUpResponse = retrofitTokenServiceWithoutInterceptor.signUp(
                userSignUp,
                "bearer ${token.AccessToken} "
            )


            if (!signUpResponse.isSuccessful) return ApiResponse(false)


            return ApiResponse(true)


        }

        suspend fun signIn(userSignIn: UserSignIn): ApiResponse<Unit> {


            var response = retrofitTokenServiceWithoutInterceptor.signIn(
                BuildConfig.ClientId_ROP,
                BuildConfig.Client_Secret_ROP,
                ApiConsts.resourceOwnerPasswordCredentialGrantType,
                userSignIn.Email,
                userSignIn.Password
            )

            if (!response.isSuccessful) return ApiResponse(false);


            var token = response.body() as Token

            //sharedPreference kaydedilecek

            return ApiResponse(true)

        }


    }

}