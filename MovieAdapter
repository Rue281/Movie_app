package com.example.android.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 *
 */
public class MovieAdapter extends BaseAdapter{
    ArrayList<Movies> MovieList;
    Context mContext = null;

    public MovieAdapter(Context context,ArrayList<Movies> movieArrayList) {
        super();
        this.mContext=context;
        this.MovieList=movieArrayList;
    }

    @Override
    public int getCount() {

        return MovieList.size();
    }

    @Override
    public Object getItem(int position) {
        return MovieList.get(position);
    }

    @Override
    public long getItemId(int id) {return MovieList.get(id).getMposter_id(); }

    @Override
    public View getView(int position, View convertedView, ViewGroup viewGroup) {
        if (convertedView==null)
        {
            LayoutInflater inflater=(LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertedView=inflater.inflate(R.layout.grid_item,null);
        }
        ImageView ivMovieName= (ImageView) convertedView.findViewById(R.id.image_view);

        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w185"+MovieList.get(position).getMposter_path()).into(ivMovieName);
        return convertedView;
    }
}
