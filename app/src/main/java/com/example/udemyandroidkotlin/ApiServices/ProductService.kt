package com.example.udemyandroidkotlin.ApiServices

import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.ApiResponse
import com.example.udemyandroidkotlin.models.Category
import com.example.udemyandroidkotlin.models.ODataModel
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.retrofitServices.ApiClient
import com.example.udemyandroidkotlin.retrofitServices.RetrofitCategoryService
import com.example.udemyandroidkotlin.retrofitServices.RetrofitProductService
import com.example.udemyandroidkotlin.utility.HelperService
import java.lang.Exception

class ProductService {

    companion object {
        private var retrofitProductServiceInterceptor =
            ApiClient.buildService(ApiConsts.apiBaseUrl, RetrofitProductService::class.java, true)


        suspend fun productList(page: Int): ApiResponse<ArrayList<Product>> {
            try {
                var response = retrofitProductServiceInterceptor.products(page);

                if (!response.isSuccessful) return HelperService.handleApiError(response)

                var oDataProduct = response.body() as ODataModel<Product>
                return ApiResponse(true, oDataProduct.Value)


            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }


        }


        suspend fun getProductById(productId: Int): ApiResponse<Product> {
            try {
                var response = retrofitProductServiceInterceptor.getProduct(productId)

                if (!response.isSuccessful) return HelperService.handleApiError(response)

                var oDataProduct = response.body() as ODataModel<Product>
                return ApiResponse(true, oDataProduct.Value[0])


            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }


        }


        suspend fun addProduct(product: Product): ApiResponse<Product> {
            try {
                var response = retrofitProductServiceInterceptor.addProduct(product)

                if (!response.isSuccessful) return HelperService.handleApiError(response)

                var oDataProduct = response.body() as ODataModel<Product>
                return ApiResponse(true, oDataProduct.Value[0])


            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }

        suspend fun updateProduct(product: Product): ApiResponse<Unit> {
            try {
                var response = retrofitProductServiceInterceptor.updateProduct(product, product.Id)

                if (!response.isSuccessful) return HelperService.handleApiError(response)

                return ApiResponse(true)


            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }


        suspend fun deleteProduct(productId: Int): ApiResponse<Unit> {
            try {
                var response = retrofitProductServiceInterceptor.deleteProduct(productId)

                if (!response.isSuccessful) return HelperService.handleApiError(response)

                return ApiResponse(true)


            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }
    }
}