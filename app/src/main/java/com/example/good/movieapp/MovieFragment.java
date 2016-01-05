package com.example.good.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    String[] movieTitle, moviePosterPath;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    ImageAdapter mImageAdater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        mImageAdater = new ImageAdapter(getActivity());
        GridView movieView = (GridView) rootView.findViewById(R.id.movieview);
        movieView.setAdapter(mImageAdater);
        updateMovie();
        movieView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MovieFragment.this.getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
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

    private void updateMovie(){
        FetchMovie fetchMovie = new FetchMovie();
        fetchMovie.execute();
    }


    public class FetchMovie extends AsyncTask<Void, Void, List<String>>{
        private final String LOG_TAG = FetchMovie.class.getSimpleName();

        private List<String> getMovies(String jsonString) throws JSONException{
            JSONObject movieJson = new JSONObject(jsonString);
            JSONArray movieArray = movieJson.getJSONArray("results");
            List<String> urls = new ArrayList<>();
            for(int i=0; i<movieArray.length(); i++){
                JSONObject movie = movieArray.getJSONObject(i);
                urls.add("http://image.tmdb.org/t/p/w342" + movie.getString("poster_path"));
            }
            return urls;
        }

        private String[] getMovieDetails(String jsonString) throws JSONException{
            JSONObject movieDetails = new JSONObject(jsonString);
            JSONArray movieDetailsArray = movieDetails.getJSONArray("results");
            String[] details=null;

            for (int i=0; i<movieDetailsArray.length(); i++){
                JSONObject detailObject = movieDetailsArray.getJSONObject(i);
                String title;
                String plot ;
                String releaseDate ;
                title = detailObject.getString("original_title");
                plot = detailObject.getString("plot");
                releaseDate = detailObject.getString("release_date");
                details[i] = title + "- " + plot + "- " + releaseDate;
            }

            return details;

        }



        @Override

        protected List<String> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String jsonString = null;
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
                jsonString = buffer.toString();


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
                return getMovies(jsonString);
            }catch (JSONException j){
                Log.e(LOG_TAG, j.getMessage(), j);
                j.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(List<String> strings) {
            mImageAdater.replace(strings);
        }

    }

    public class ImageAdapter extends BaseAdapter{
        private final String LOG_TAG = ImageAdapter.class.getSimpleName();
        private final Context context;
        private final List<String> urls = new ArrayList<String>();

            public ImageAdapter(Context context){
            this.context = context;
            //Collections.addAll(urls, moviePosterPath);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView;

            if(convertView==null){

                gridView = new View(context);
                gridView = inflater.inflate(R.layout.image, null);

                ImageView movieView = (ImageView) gridView.findViewById(R.id.image);

                String url = getItem(position);

                Log.e(LOG_TAG, "URL: " + url);
                //int width= context.getResources().getDisplayMetrics().widthPixels;
                Picasso.with(context).load(url).into(movieView);
                return gridView;
            }
            else
            {
                gridView = (View) convertView;
            }

            return gridView;


        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public String getItem(int position) {
            return urls.get(position);
        }

        @Override
        public int getCount() {
            return urls.size();
        }
        public void replace(List<String> urls) {
            this.urls.clear();
            this.urls.addAll(urls);
            notifyDataSetChanged();
        }
    }

}




























