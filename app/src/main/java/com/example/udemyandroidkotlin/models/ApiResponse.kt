package com.example.udemyandroidkotlin.models

data class ApiResponse<T>(var isSuccessful:Boolean,var success:T?=null, var fail:ApiError?=null)