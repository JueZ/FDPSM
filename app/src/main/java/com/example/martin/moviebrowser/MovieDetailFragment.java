package com.example.martin.moviebrowser;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.LayoutInflater;

/**
 * Created by Martin on 08.11.2015.
 */
public class MovieDetailFragment extends Fragment {

    public static MovieDetailFragment newInstance(String movie) {
        MovieDetailFragment f = new MovieDetailFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("movie", movie);
        f.setArguments(args);

        return f;
    }

    public String getShownIndex() {
        return getArguments().getString("movie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moviedetail, container, false);
    }

}
