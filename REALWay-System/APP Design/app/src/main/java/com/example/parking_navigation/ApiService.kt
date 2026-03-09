package com.example.parking_navigation

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url


interface ApiService {

    @POST("/update_client_data_park")
    fun updateClientDataPark(@Body requestData: RequestData): Call<ResponseData>

    @POST("/update_client_data_navigate")
    fun updateClientDataNavigate(@Body requestData: RequestData): Call<ResponseData>


    @GET("/health")
    suspend fun health(): Response<Map<String, String>>


}

data class RequestData(val uuid: String, val action: String)
data class ResponseData(val message: String, val tagNumber : String, val status: String)
