package com.example.udemyandroidkotlin.models

import com.google.gson.annotations.SerializedName

data class ODataModel<T>(
    @SerializedName("value") var Value:ArrayList<T>
) {
}