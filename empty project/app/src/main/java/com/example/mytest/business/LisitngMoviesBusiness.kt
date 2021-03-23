package com.example.mytest.business

import android.view.View
import android.widget.TextView
import com.example.mytest.R
import com.example.mytest.model.Movie

fun listingInjection(view: View, moviesListing: List<Movie>){
    if (moviesListing.isNotEmpty()) {
        val movieTitle = view.findViewById<TextView>(R.id.temp_first_movie)
        movieTitle.text = moviesListing[0].title
    }
}