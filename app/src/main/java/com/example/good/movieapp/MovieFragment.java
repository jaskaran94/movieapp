package com.example.good.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.good.movieapp.adapters.ImageAdapter;
import com.example.good.movieapp.api.FetchMovie;
import com.example.good.movieapp.model.Movie;

import java.util.ArrayList;

public class MovieFragment extends Fragment {
    final String LOG_TAG = FetchMovie.class.getSimpleName();
    private ImageAdapter mMoviePosterAdapter;
    private SharedPreferences prefs;
    private String sortOrder;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public MovieFragment() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortOrder = prefs.getString("sortType", "popularity.desc");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        // get sort order to see if it has recently changed
        super.onStart();
        String prefSortOrder = prefs.getString("sortType", "popularity.desc");
        if(movies.size() > 0 &&prefSortOrder.equals(sortOrder)){
            updatePosterAdapter();
        }else{
            sortOrder = prefSortOrder;
            getMovies();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        mMoviePosterAdapter = new ImageAdapter(getActivity(),
                R.layout.list_item_poster,
                R.id.list_item_poster_imageview,
                new ArrayList<String>());
        GridView movieView = (GridView) rootView.findViewById(R.id.movieview);
        getMovies();
        movieView.setAdapter(mMoviePosterAdapter);

        movieView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie details = movies.get(position);
                Intent intent = new Intent(getActivity(), DetailsActivity.class)
                        .putExtra("movies_details", details);
                startActivity(intent);

            }
        });

        return rootView;

    }


    private void getMovies() {
        FetchMovie fetchMovie = new FetchMovie(getActivity(),
                movies,
                mMoviePosterAdapter,
                sortOrder);
        fetchMovie.execute();
    }

    // updates the ArrayAdapter of poster images
   private void updatePosterAdapter() {
        mMoviePosterAdapter.clear();
        for(Movie movie : movies) {
            mMoviePosterAdapter.add(movie.getPoster());
        }
    }

}



























