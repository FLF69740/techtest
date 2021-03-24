package com.example.mytest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.model.Movie

class moviesListingAdapter(private val movies: List<Movie>, private val clickListener: (Pair<String?, String?>) -> Unit) : RecyclerView.Adapter<moviesLisitingHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): moviesLisitingHolder = moviesLisitingHolder(LayoutInflater.from(parent.context), parent)
    override fun onBindViewHolder(holder: moviesLisitingHolder, position: Int) = holder.setItem(movies[position], clickListener)
    override fun getItemCount(): Int = movies.size
}