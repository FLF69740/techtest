package com.example.mytest.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import java.util.ArrayList

@VersionedParcelize
data class MoviesListing(
        val movies: ArrayList<Movie>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Movie)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(movies)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoviesListing> {
        override fun createFromParcel(parcel: Parcel): MoviesListing {
            return MoviesListing(parcel)
        }

        override fun newArray(size: Int): Array<MoviesListing?> {
            return arrayOfNulls(size)
        }
    }
}
