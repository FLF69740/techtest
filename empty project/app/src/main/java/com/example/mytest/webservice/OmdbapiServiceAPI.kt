package com.example.mytest.webservice

import retrofit2.Retrofit

class OmdbapiServiceAPI(private val retrofit: Retrofit) {

    fun getOmdbapiService() : OmdbapiService{
        return retrofit.create(OmdbapiService::class.java)
    }

}