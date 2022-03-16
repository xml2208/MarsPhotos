package com.bignerdranch.android.marsphotos

import com.bignerdranch.android.marsphotos.network.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarsApiServiceTests : BaseTest() {
    private lateinit var service: ApiService

    @Before
    fun setUp() {
        val url = mockWebServer.url("/")
        service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun api_service(){
        enqueue("mars_photos.json")
        runBlocking {
            val apiResponse = service.getPhotos()
            Assert.assertNotNull(apiResponse)
//            Assert.assertTrue("THe list was empty", apiResponse.isEmpty())    \\\\this is the incorrect test)))
            Assert.assertEquals("Id didn't match", "424905", apiResponse[0].id)
        }
    }
}