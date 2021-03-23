package com.example.mytest.webservice

import com.example.mytest.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface OmdbapiService {

    @GET("?plot=full")
    suspend fun getListMovies(@Query("apikey") apiKey: String?, @Query("s") wordKey: String?, @Query("page") pageNumber: Int?): MoviesResponse?


}