package com.example.udemyandroidkotlin.models

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access_token") var AccessToken: String,
    @SerializedName("expires_in") var Expires: Int,
    @SerializedName("token_type") var TokenType: String,
    @SerializedName("refresh_token") var RefreshToken: String,
    @SerializedName("scope") var Scope: String
) {
}