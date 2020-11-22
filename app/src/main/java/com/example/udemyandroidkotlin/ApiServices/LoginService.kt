package com.example.udemyandroidkotlin.ApiServices

import com.example.udemyandroidkotlin.BuildConfig
import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.ApiResponse
import com.example.udemyandroidkotlin.models.TokenAPI
import com.example.udemyandroidkotlin.models.UserSignIn
import com.example.udemyandroidkotlin.models.UserSignUp
import com.example.udemyandroidkotlin.retrofitServices.ApiClient
import com.example.udemyandroidkotlin.retrofitServices.RetrofitLoginService
import com.example.udemyandroidkotlin.utility.HelperService
import java.lang.Exception

class LoginService {

    companion object {
        private var retrofitTokenServiceWithoutInterceptor =
            ApiClient.buildService(ApiConsts.authBaseUrl, RetrofitLoginService::class.java, false)


        suspend fun signUp(userSignUp: UserSignUp): ApiResponse<Unit> {


            try {
                var tokenResponse = TokenService.getTokenWithClientCredentials();

                if (!tokenResponse.isSuccessful) return ApiResponse(false,fail = tokenResponse.fail)


                var token = tokenResponse.success!!

                var signUpResponse = retrofitTokenServiceWithoutInterceptor.signUp(
                    userSignUp,
                    "bearer ${token.AccessToken} "
                )


                if (!signUpResponse.isSuccessful) return HelperService.handleApiError(signUpResponse)


                return ApiResponse(true)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }


        }

        suspend fun signIn(userSignIn: UserSignIn): ApiResponse<Unit> {

            try {
                var response = retrofitTokenServiceWithoutInterceptor.signIn(
                    BuildConfig.ClientId_ROP,
                    BuildConfig.Client_Secret_ROP,
                    ApiConsts.resourceOwnerPasswordCredentialGrantType,
                    userSignIn.Email,
                    userSignIn.Password
                )





                if (!response.isSuccessful) return HelperService.handleApiError(response)


                var token = response.body() as TokenAPI

                HelperService.saveTokenSharedPreference(token)

                return ApiResponse(true)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }


        }


    }

}