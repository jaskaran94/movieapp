package com.example.good.movieapp.model;

/*
*
* simple object to store all the information
* for a movie being used in the UI
*
* Takes a string for the movie title, poster, overview, voteAverage
* and releaseDate
*
* Implements parcelable in order to easily pass between intents
*
* */

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String poster;
    private String overview;
    private String voteAverage;
    private String releaseDate;
    private String movieReviews;
    private String movieTrailers="";

    public Movie(int id, String title, String poster, String overview,
                 String voteAverage, String releaseDate){
        this.id=id;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReviews(){
        return movieReviews;
    }

    public void setReviews(String reviews){
        movieReviews = reviews;
    }

    public String getMovieTrailers(){
        return movieTrailers;
    }

    public void setMovieTrailers(String trailers){
        movieTrailers = trailers;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(poster);
        out.writeString(overview);
        out.writeString(voteAverage);
        out.writeString(releaseDate);
        out.writeString(movieReviews);
        out.writeString(movieTrailers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
        movieReviews = in.readString();
        movieTrailers = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}