package com.example.good.movieapp.data_v2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pc on 2/18/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "movies.db";

    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE" + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL," +
                MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_URI + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " REAL NOT NULL," +
                MovieContract.MovieEntry.COLUMN_MOVIE_REVIEWS + " TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_TRAILERS + " TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT + " INTEGER NOT NULL" + ");";

        final String SQL_CREATE_TRAILERS_TABLE = "CREATE TABLE " + MovieContract.TrailerEntry.TABLE_NAME + " ( "
                + MovieContract.TrailerEntry._ID + " INTEGER PRIMARY KEY, "
                + MovieContract.TrailerEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + MovieContract.TrailerEntry.COLUMN_YOUTUBE_KEY + " TEXT NOT NULL, "
                + MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, "
                + MovieContract.TrailerEntry.COLUMN_TRAILER_ID + " TEXT NOT NULL, "
                + " FOREIGN KEY (" + MovieContract.TrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES "
                + MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry.COLUMN_MOVIE_ID + "),"
                + "UNIQUE (" + MovieContract.TrailerEntry.COLUMN_TRAILER_ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_REVIEWS_TABLE = "CREATE TABLE " + MovieContract.ReviewEntry.TABLE_NAME + " ( "
                + MovieContract.ReviewEntry._ID + " INTEGER PRIMARY KEY, "
                + MovieContract.ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, "
                + MovieContract.ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, "
                + MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, "
                + MovieContract.ReviewEntry.COLUMN_REVIEW_ID + " TEXT NOT NULL, "
                + " FOREIGN KEY (" + MovieContract.ReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES "
                + MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry.COLUMN_MOVIE_ID + "),"
                + "UNIQUE (" + MovieContract.ReviewEntry.COLUMN_REVIEW_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_TRAILERS_TABLE);
        db.execSQL(SQL_CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.TrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.ReviewEntry.TABLE_NAME);
        onCreate(db);
    }
}
