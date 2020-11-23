package com.example.udemyandroidkotlin.utility


import android.content.Context
import android.widget.Toast
import com.example.udemyandroidkotlin.Exceptions.OfflineException

import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.models.ApiError
import com.example.udemyandroidkotlin.models.ApiResponse
import com.example.udemyandroidkotlin.models.TokenAPI
import com.google.gson.Gson
import retrofit2.Response

class HelperService {


    companion object {

        fun <T> handleException(ex: Exception): ApiResponse<T> {
            return when (ex) {

                is OfflineException -> {

                    val exmessage =
                        arrayListOf(GlobalApp.getAppContext().resources.getString(R.string.ex_no_exception))
                    var apiError = ApiError(exmessage, 500, true)

                    ApiResponse(false, fail = apiError)


                }


                else -> {
                    val exmessage =
                        arrayListOf(GlobalApp.getAppContext().resources.getString(R.string.ex_common_error))
                    var apiError = ApiError(exmessage, 500, true)

                    ApiResponse(false, fail = apiError)

                }


            }


        }


        fun saveTokenSharedPreference(token: TokenAPI) {

            var preference =
                GlobalApp.getAppContext().getSharedPreferences("token_api", Context.MODE_PRIVATE)


            var editor = preference.edit();

            editor.putString("token", Gson().toJson(token))

            editor.apply()


        }

        fun getTokenSharedPreference(): TokenAPI? {
            var preference =
                GlobalApp.getAppContext().getSharedPreferences("token_api", Context.MODE_PRIVATE)

            var tokenString: String? = preference.getString("token", null) ?: return null


            return Gson().fromJson(tokenString, TokenAPI::class.java)


        }


        fun <T1, T2> handleApiError(response: Response<T1>): ApiResponse<T2> {
            var apiError: ApiError? = null;

            if (response.errorBody() != null) {
                var errorBody = response.errorBody()!!.string()

                apiError = Gson().fromJson(errorBody, ApiError::class.java)
            }


            return ApiResponse(false, null, apiError)
        }

        fun showErrorMessageByToast(apiError: ApiError?) {

            if (apiError == null) return
            var errorBuilder = StringBuilder()

            if (apiError.IsShow) {
                for (error in apiError.Errors) {
                    errorBuilder.append(error + "\n")
                }

            }

            Toast.makeText(GlobalApp.getAppContext(), errorBuilder.toString(), Toast.LENGTH_LONG)
                .show()


        }

    }
}