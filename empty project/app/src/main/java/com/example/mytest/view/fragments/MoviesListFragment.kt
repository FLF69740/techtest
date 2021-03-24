package com.example.mytest.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.R
import com.example.mytest.business.listingInjection
import com.example.mytest.model.Movie
import com.example.mytest.view.adapter.moviesListingAdapter

class MoviesListFragment : Fragment() {

    private lateinit var mAdapter: moviesListingAdapter
    private val mListOfMovies = mutableListOf<Movie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.movies_recyclerview)

        mAdapter = moviesListingAdapter(mListOfMovies) { key: Pair<String?, String?> ->
            Toast.makeText(view.context, "VOTE FOR : ${key.first}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.apply {
            this.adapter = mAdapter
            this.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }

    fun listingUpdate(movies: List<Movie>){ listingInjection(movies, mAdapter, mListOfMovies) }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}