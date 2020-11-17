package com.example.udemyandroidkotlin.models

import com.google.gson.annotations.SerializedName

data class ApiError(

    @SerializedName("errors")
    var Errors:ArrayList<String>,
    @SerializedName("status")
    var Status:Number,
    @SerializedName("isShow")
    var IsShow:Boolean) {
}