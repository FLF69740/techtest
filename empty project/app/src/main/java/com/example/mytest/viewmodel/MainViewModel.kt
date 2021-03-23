package com.example.mytest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytest.model.Movie
import com.example.mytest.model.MoviesResponse
import com.example.mytest.remotedatasource.MoviesRepository
import com.example.mytest.utils.MOVIES_LISTING_TAG
import com.example.mytest.webservice.OmdbapiServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.math.log

class MainViewModel(serviceAPI: OmdbapiServiceAPI) : ViewModel() {

    val moviesResponse = MutableLiveData<MoviesResponse>()
    private val service = serviceAPI

    fun response(apiKey: String?, wordKey: String?, pageNumber: Int?){
        viewModelScope.launch(Dispatchers.Default) {
            try {
                moviesResponse.postValue(MoviesRepository(serviceAPI = service).askOmdbapi(apiKey, wordKey, pageNumber))
            } catch (e : IOException){
                Log.e(MOVIES_LISTING_TAG, "response: WEBSERVICE FAILED")
            }

        }
    }

}