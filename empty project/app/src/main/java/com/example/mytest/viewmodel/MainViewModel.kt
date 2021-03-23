package com.example.mytest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytest.model.MoviesResponse
import com.example.mytest.remotedatasource.MoviesRepository
import com.example.mytest.webservice.OmdbapiServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(serviceAPI: OmdbapiServiceAPI) : ViewModel() {

    val moviesResponse = MutableLiveData<MoviesResponse>()
    private val service = serviceAPI

    fun response(apiKey: String?, wordKey: String?, pageNumber: Int?){
        viewModelScope.launch(Dispatchers.Default) {
            moviesResponse.postValue(MoviesRepository(serviceAPI = service).askOmdbapi(apiKey, wordKey, pageNumber))
        }
    }

}