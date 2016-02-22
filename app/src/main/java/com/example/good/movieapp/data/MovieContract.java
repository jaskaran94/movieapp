package com.example.good.movieapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pc on 2/18/2016.
 */
public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.good.movieapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_REVIEWS = "reviews";
    public static final String PATH_TRAILERS = "trailers";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        //table name
        public static final String TABLE_NAME = "movies";

        //columns
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_MOVIE_BACKDROP_URI = "backdrop_path";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "vote_count";
        public static final String COLUMN_MOVIE_TITLE = "original_title";
        public static final String COLUMN_MOVIE_POSTER = "poster_path";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";
        public static final String COLUMN_FAVORITE = "favorite";

        public static String getPosterUrlFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }

        public static long getIdFromUri(Uri uri){
            return ContentUris.parseId(uri);
        }

        public static Uri buildMovieWithId(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TrailerEntry implements BaseColumns{
        //content uri for trailer entry
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;

        public static final String TABLE_NAME = "trailers";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_YOUTUBE_KEY = "youtube_key";
        public static final String COLUMN_TRAILER_ID = "trailer_id";
        public static final String COLUMN_MOVIE_ID = "movie_id"; //the movie id from backend used for joins

        public static long getMovieIdFromUri(Uri uri){
            return ContentUris.parseId(uri);
        }

        public static Uri buildTrailerWithId(long movieId){
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }
    }

    public static final class ReviewEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;

        public static final String TABLE_NAME = "reviews";

        //columns
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_REVIEW_ID = "review_id";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static long getMovieIdFromUri(Uri uri){
            return ContentUris.parseId(uri);
        }

        public static Uri buildReviewWithId(long insertedId){
            return ContentUris.withAppendedId(CONTENT_URI, insertedId);
        }
    }

}




























