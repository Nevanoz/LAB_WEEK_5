package com.example.lab_week_5.api

import com.example.lab_week_5.model.ImageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("images/search")
    fun searchImages(
        @Query("limit") limit: Int,
        @Query("size") size: String
    ): Call<List<ImageData>>
}
