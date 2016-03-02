package com.example.good.movieapp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.good.movieapp.model.Review;

public class ReviewAdapter extends ArrayAdapter<Review>{
    public ReviewAdapter(Context context, int resource) {
        super(context, resource);
    }
}