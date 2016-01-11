package com.example.good.movieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by pc on 1/11/2016.
 */
public class FetchMovie extends AsyncTask<Void, Void, List<Movie>> {
    public AsyncResponse delegate;
    private final String LOG_TAG = FetchMovie.class.getSimpleName();
    private final String MOVIE_POSTER_BASE = "http://image.tmdb.org/t/p/";
    private final String MOVIE_POSTER_SIZE = "w185";

    public FetchMovie(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override

    protected List<Movie> doInBackground(Void... params) {

        String movieJsonStr = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String sort_by = "popularity.desc";
        int page = 1;

        try{

            final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
            final String PAGE_PARAM = "page";
            final String SORT_BY_PARAM = "popularity.desc";
            final String APP_ID = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(PAGE_PARAM, Integer.toString(page))
                    .appendQueryParameter(SORT_BY_PARAM, sort_by)
                    .appendQueryParameter(APP_ID, BuildConfig.MOVIE_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream==null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");

            }
            if(buffer.length()==0){
                return null;
            }
            movieJsonStr = buffer.toString();


        }catch (final IOException e){
            Log.e(LOG_TAG, "Error closing stream", e);
            return null;
        }finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                }catch(final IOException e){
                    Log.e(LOG_TAG, "error closing reader", e);
                }
            }
        }

        try {
            return extractData(movieJsonStr);
        }catch (JSONException j){
            Log.e(LOG_TAG, j.getMessage(), j);
            j.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(List<Movie> results) {
        if (results != null) {
            // return the List of movies back to the caller.
            delegate.onTaskCompleted(results);
        }
    }

    private String getYear(String date){
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Integer.toString(cal.get(Calendar.YEAR));
    }

    private List<Movie> extractData(String moviesJsonStr) throws JSONException {

        // Items to extract
        final String ARRAY_OF_MOVIES = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(ARRAY_OF_MOVIES);
        int moviesLength =  moviesArray.length();
        List<Movie> movies = new ArrayList<Movie>();

        for(int i = 0; i < moviesLength; ++i) {

            // for each movie in the JSON object create a new
            // movie object with all the required data
            JSONObject movie = moviesArray.getJSONObject(i);
            String title = movie.getString(ORIGINAL_TITLE);
            String poster = MOVIE_POSTER_BASE + MOVIE_POSTER_SIZE + movie.getString(POSTER_PATH);
            String overview = movie.getString(OVERVIEW);
            String voteAverage = movie.getString(VOTE_AVERAGE);
            String releaseDate = getYear(movie.getString(RELEASE_DATE));

            movies.add(new Movie(title, poster, overview, voteAverage, releaseDate));

        }

        return movies;

    }

}

