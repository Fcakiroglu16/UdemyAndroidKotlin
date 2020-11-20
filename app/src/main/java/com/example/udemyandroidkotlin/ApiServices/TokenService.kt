package com.example.udemyandroidkotlin.ApiServices

import android.content.Context
import com.example.udemyandroidkotlin.BuildConfig
import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.models.ApiResponse
import com.example.udemyandroidkotlin.models.Introspec
import com.example.udemyandroidkotlin.models.TokenAPI
import com.example.udemyandroidkotlin.retrofitServices.ApiClient
import com.example.udemyandroidkotlin.retrofitServices.RetrofitTokenService
import com.example.udemyandroidkotlin.utility.GlobalApp
import com.example.udemyandroidkotlin.utility.HelperService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class TokenService {

    companion object {

        private var retrofitTokenServiceWithoutInterceptor =
            ApiClient.buildService(ApiConsts.authBaseUrl, RetrofitTokenService::class.java, false)


        suspend fun getTokenWithClientCredentials(): ApiResponse<TokenAPI> {


            try {
                var response = retrofitTokenServiceWithoutInterceptor.getTokenWithClientCredentials(
                    BuildConfig.ClientId_CC,
                    BuildConfig.Client_Secret_CC,
                    ApiConsts.clientCredentialGrantType
                )

///Helper method yaz basarısız olan durumlar için
                if (!response.isSuccessful) return ApiResponse(false)

                return ApiResponse(true, response.body() as TokenAPI)
            } catch (ex: Exception) {
                return HelperService.handleException(ex);
            }


        }

        fun refreshToken(refreshToken: String): ApiResponse<TokenAPI> {

            try {
                var response = retrofitTokenServiceWithoutInterceptor.refreshToken(
                    BuildConfig.ClientId_ROP,
                    BuildConfig.Client_Secret_ROP,
                    ApiConsts.resourceOwnerPasswordCredentialGrantType,
                    refreshToken
                ).execute()

                return if (response.isSuccessful) {
                    ApiResponse(true, response.body() as TokenAPI)
                } else {
                    ApiResponse(false)
                }
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }


        }


        suspend fun checkToken(): ApiResponse<Unit> {

            try {
                var preference =
                    GlobalApp.getAppContext().getSharedPreferences("ApiToken", Context.MODE_PRIVATE)

                var tokenString: String? =
                    preference.getString("token", null) ?: return ApiResponse(false);


                var token: TokenAPI = Gson().fromJson(tokenString, TokenAPI::class.java)

                var authorization: String =
                    okhttp3.Credentials.basic("resource_product_api", "apisecret")


                var response =
                    retrofitTokenServiceWithoutInterceptor.checkToken(
                        token.AccessToken,
                        authorization
                    )

                if (!response.isSuccessful) return ApiResponse(false)


                var introspec = response.body() as Introspec


                if (!introspec.Active) return ApiResponse(false)


                return ApiResponse(true)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }


    }


}