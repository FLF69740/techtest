package com.example.mytest.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
        @SerializedName("Search") val moviesList: List<Movie>,
        @SerializedName("totalResults") val resultNumber: String?,
        @SerializedName("Response") val response: String?,
        @SerializedName("Error") val error: String?
)
