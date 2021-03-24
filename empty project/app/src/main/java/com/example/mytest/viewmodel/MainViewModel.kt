package com.example.mytest.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.mytest.model.MoviesResponse
import com.example.mytest.remotedatasource.MoviesRepository
import com.example.mytest.utils.MOVIES_LISTING_TAG
import com.example.mytest.webservice.OmdbapiServiceAPI
import kotlinx.coroutines.*
import java.io.IOException

class MainViewModel(serviceAPI: OmdbapiServiceAPI) : ViewModel() {

    val moviesResponse = MediatorLiveData<List<MoviesResponse>>()
    private val service = serviceAPI

    fun response(apiKey: String?, wordKey: String?, pageNumber: Int?){
        viewModelScope.launch(Dispatchers.IO) {
            try {

                coroutineScope{
                    launch {
                        val mr = async { (MoviesRepository(serviceAPI = service).askOmdbapi(apiKey, wordKey, pageNumber)) }

                        val listOfDeferredCall = mutableListOf<Deferred<MoviesResponse>>()

                        val firstMoviesResponsePage = mr.await()

                        var pages: Int = (firstMoviesResponsePage.resultNumber!!.toInt())/10
                        if ((firstMoviesResponsePage.resultNumber.toInt()) % 10 != 0) pages +=1

                        for (page in 2..pages) {
                            listOfDeferredCall.add(async { (MoviesRepository(serviceAPI = service).askOmdbapi(apiKey, wordKey, page)) })
                        }

                        val fin = mutableListOf<MoviesResponse>()
                        fin.add(firstMoviesResponsePage)
                        for (resB in listOfDeferredCall) fin.add(resB.await())

                        moviesResponse.postValue(fin)
                    }
                }
            } catch (e : IOException){
                Log.e(MOVIES_LISTING_TAG, "response: WEBSERVICE FAILED")
            }

        }
    }

}