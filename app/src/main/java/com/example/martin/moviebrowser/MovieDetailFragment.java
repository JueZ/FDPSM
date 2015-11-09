package com.example.martin.moviebrowser;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Martin on 08.11.2015.
 */
public class MovieDetailFragment extends Fragment implements MovieAPIAsync.AsyncListener {

    private Bitmap bitmap;

    public static MovieDetailFragment newInstance(String movie) {
        MovieDetailFragment f = new MovieDetailFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("movie", movie);
        f.setArguments(args);

        return f;
    }

    public String getMovieName() {
        return getArguments().getString("movie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("DETAIL", getMovieName());

        MovieAPIAsync movieAPIAsync = new MovieAPIAsync(MovieDetailFragment.this, 'i');
        String query = Uri.encode(getMovieName());
        String uri = "http://www.omdbapi.com/?t=" + query;

        movieAPIAsync.execute(uri);

        View view = inflater.inflate(R.layout.fragment_moviedetail, container, false);

        TextView textView = (TextView) view.findViewById(R.id.textViewTitle);
        textView.setText(getMovieName());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void setResults(ArrayList<Movie> results) {

        if(results.size() > 0)
        {
            final Movie movie = results.get(0);

            TextView textView = (TextView) getView().findViewById(R.id.textViewTitle);
            textView.setText(movie.getTitle());

            textView = (TextView) getView().findViewById(R.id.textViewRuntime);
            textView.setText(movie.getRuntime());

            textView = (TextView) getView().findViewById(R.id.textViewYearOfRelease);
            textView.setText(movie.getYear());

            ImageView imageViewPoster = (ImageView) getView().findViewById(R.id.imageView);
            if(movie.getPosterBitmap() == null)  {
                imageViewPoster.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),
                        R.drawable.ic_not_found));

            }
            else imageViewPoster.setImageBitmap(movie.getPosterBitmap());




        }

    }
}
