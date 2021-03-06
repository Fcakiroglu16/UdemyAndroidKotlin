package com.example.udemyandroidkotlin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("Id") var Id:Int,
    @SerializedName("Name") var Name:String,
    @SerializedName("Price") var Price:Double,
    @SerializedName("Color") var Color:String,
    @SerializedName("Stock") var Stock:Int,
    @SerializedName("PhotoPath") var PhotoPath:String,
    @SerializedName("Category_Id") var CategoryId:Int,
    @SerializedName("Category") var Category:Category?

) : Parcelable

/*
"Id": 3,
"Name": "Kalem 2",
"Price": 100.00,
"Color": "Kırmızı",
"Stock": 20,
"PhotoPath": null,
"Category_Id": 2*/
