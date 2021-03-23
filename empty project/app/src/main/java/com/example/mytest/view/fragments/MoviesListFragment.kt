package com.example.mytest.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytest.R
import com.example.mytest.business.listingInjection
import com.example.mytest.model.Movie

class MoviesListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_movies_list, container, false)

    companion object {
        fun newInstance() = MoviesListFragment()
        fun listingUpdate(view: View, movies: List<Movie>){ listingInjection(view, movies) }
    }
}