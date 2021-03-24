package com.example.mytest.business

import android.view.View
import android.widget.Adapter
import android.widget.TextView
import com.example.mytest.R
import com.example.mytest.model.Movie
import com.example.mytest.view.adapter.moviesListingAdapter

fun listingInjection(moviesListingToInject: List<Movie>, adapter: moviesListingAdapter, moviesListing: MutableList<Movie>){
    if (moviesListingToInject.isNotEmpty()) {
        moviesListing.clear()
        moviesListing.addAll(moviesListingToInject)
        adapter.notifyDataSetChanged()
    }
}