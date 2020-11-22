package com.example.udemyandroidkotlin.retrofitServices

import com.example.udemyandroidkotlin.models.TokenAPI
import com.example.udemyandroidkotlin.models.UserSignUp
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitLoginService {

    @POST("api/user/signup")
    suspend fun signUp(
        @Body userSignUp: UserSignUp,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>


    @POST("connect/token")
    @FormUrlEncoded
    suspend fun signIn(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("username") userName: String,
        @Field("password") password: String

    ):Response<TokenAPI>


}