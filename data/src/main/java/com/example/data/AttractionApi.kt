package com.example.data

import com.example.data.dto.AttractionRepone
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AttractionApi {
    @GET("{lang}/Attractions/All")
    suspend fun getAll(
        @Path("lang") lang: String = "en",
        @Query("page") page: Int = 1
    ): Response<AttractionRepone>
}