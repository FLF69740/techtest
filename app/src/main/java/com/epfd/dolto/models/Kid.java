package com.epfd.dolto.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Kid implements Parcelable {

    private String mNom;
    private String mPrenom;
    private String mClasse;
    private String mGenre;

    public Kid(String nom, String prenom, String classe, String genre) {
        mNom = nom;
        mPrenom = prenom;
        mClasse = classe;
        mGenre = genre;
    }

    public String getNom() {
        return mNom;
    }

    public void setNom(String nom) {
        mNom = nom;
    }

    public String getPrenom() {
        return mPrenom;
    }

    public void setPrenom(String prenom) {
        mPrenom = prenom;
    }

    public String getClasse() {
        return mClasse;
    }

    public void setClasse(String classe) {
        mClasse = classe;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    protected Kid(Parcel in) {
        mNom = in.readString();
        mPrenom = in.readString();
        mClasse = in.readString();
        mGenre = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNom);
        dest.writeString(mPrenom);
        dest.writeString(mClasse);
        dest.writeString(mGenre);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Kid> CREATOR = new Parcelable.Creator<Kid>() {
        @Override
        public Kid createFromParcel(Parcel in) {
            return new Kid(in);
        }

        @Override
        public Kid[] newArray(int size) {
            return new Kid[size];
        }
    };
}
