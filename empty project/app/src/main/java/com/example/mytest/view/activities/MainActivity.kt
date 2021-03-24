package com.example.mytest.view.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mytest.viewmodel.MainViewModel
import com.example.mytest.R
import com.example.mytest.model.Movie
import com.example.mytest.model.MoviesResponse
import com.example.mytest.utils.MOVIES_LISTING_TAG
import com.example.mytest.view.fragments.MoviesListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val mViewModel: MainViewModel by viewModel()

    private val apiKey = "493d76b9"
    private val wordKey = "pirate"

    override fun getAttachedLayout(): Int = R.layout.activity_main
    override fun getFragmentLayout(): Int = R.id.movies_listing_fragment
    override fun getFragment(): Fragment = MoviesListFragment.newInstance()
    override fun getFragmentTag() { tag = MOVIES_LISTING_TAG }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityObservation()

        mViewModel.response(apiKey, wordKey, 1)
    }

    // LIFECYCLE

    private fun activityObservation(){
        mViewModel.moviesResponse.observe(this, { addMovies(it) })
    }

    // API RESPONSE

    private fun addMovies(response: List<MoviesResponse>?){
        response?.let {
            if (response[0].error == null){
                val movies = mutableListOf<Movie>()

                for (a in response) {
                    for (b in a.moviesList) movies.add(b)
                }

                moviesListingFragmentRefresh(movies)
            }
        }
    }

    // FRAGMENT UPDATE

    private fun moviesListingFragmentRefresh(movies: List<Movie>){
        val fragment = supportFragmentManager.findFragmentByTag(tag) as MoviesListFragment
        fragment.view?.let { fragment.listingUpdate(movies) }
    }


}