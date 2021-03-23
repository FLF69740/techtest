package com.example.mytest

import com.example.mytest.utils.BASE_URL
import com.example.mytest.viewmodel.MainViewModel
import com.example.mytest.webservice.OmdbapiServiceAPI
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { OmdbapiServiceAPI(get()) }
    single {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}