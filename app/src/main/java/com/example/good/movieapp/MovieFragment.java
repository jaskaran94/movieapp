package com.example.good.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {
    final String LOG_TAG = FetchMovie.class.getSimpleName();
    private ImageAdapter mMoviePosterAdapter;
    List<Movie> movies = new ArrayList<Movie>();
    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getMovies() {
        FetchMovie fetchMoviesTask = new FetchMovie(new AsyncResponse() {
            @Override
            public void onTaskCompleted(List<Movie> results) {
                movies.clear();
                movies.addAll(results);
                updatePosterAdapter();

            }
        });
        fetchMoviesTask.execute();
    }

    // updates the ArrayAdapter of poster images
   private void updatePosterAdapter() {
        mMoviePosterAdapter.clear();
        for(Movie movie : movies) {
            mMoviePosterAdapter.add(movie.getPoster());
        }
    }

}



























