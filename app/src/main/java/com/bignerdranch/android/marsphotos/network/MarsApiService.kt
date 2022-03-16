package com.bignerdranch.android.marsphotos.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

     val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()


interface ApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>

}

object MarsApi {
     val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}