package com.example.mytest.remotedatasource

import androidx.lifecycle.LiveData
import com.example.mytest.model.MoviesResponse
import com.example.mytest.webservice.OmdbapiServiceAPI

class MoviesRepository(val serviceAPI: OmdbapiServiceAPI) {
    suspend fun askOmdbapi(apiKey: String?, wordKey: String?, pageNumber: Int?): MoviesResponse {
        return serviceAPI.getOmdbapiService().getListMovies(apiKey, wordKey, pageNumber)
    }
}