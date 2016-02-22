package com.example.good.movieapp;

import com.example.good.movieapp.model.Movie;

import java.util.List;

/*
*
* Custom interface to impliment
* a call back function that returns results
* from an async task
*
* */

public interface AsyncResponse {

    void onTaskCompleted(List<Movie> results);

}
