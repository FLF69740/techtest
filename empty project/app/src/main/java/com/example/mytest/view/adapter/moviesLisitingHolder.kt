package com.example.mytest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytest.R
import com.example.mytest.model.Movie

class moviesLisitingHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.movie_item, parent, false)) {

    fun setItem(movie: Movie, clickListener: (Pair<String?, String?>) -> Unit){
        val picture: ImageView = itemView.findViewById(R.id.movie_picture)
        val title: TextView = itemView.findViewById(R.id.movie_title)
        val year: TextView = itemView.findViewById(R.id.movie_year)

        title.text = movie.title
        year.text = movie.year

        Glide.with(itemView).load(movie.poster).into(picture)

        itemView.setOnClickListener {
            clickListener(Pair(movie.title, movie.id))
        }

    }




}