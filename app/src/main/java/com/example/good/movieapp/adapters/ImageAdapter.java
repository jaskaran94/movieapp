package com.example.good.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pc on 1/11/2016.
*/
public class ImageAdapter extends ArrayAdapter<String> {
    private final String LOG_TAG = ImageAdapter.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    private Context context;
    private int layoutId;
    private int imageViewID;

    public ImageAdapter(Context context, int layoutId, int imageViewID, ArrayList<String> urls) {
        super(context, 0, urls);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.layoutId = layoutId;
        this.imageViewID = imageViewID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        String url;
        if(v==null) {
            v = mLayoutInflater.inflate(layoutId, parent, false);
        }
            ImageView imageView = (ImageView) v.findViewById(imageViewID);
            url = getItem(position);
            Picasso.with(context).load(url).into(imageView);
        return v;
        }

}