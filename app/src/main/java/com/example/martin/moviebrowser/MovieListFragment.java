package com.example.martin.moviebrowser;

import android.app.Activity;
import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 08.11.2015.
 */
public class MovieListFragment extends ListFragment implements MovieAPIAsync.AsyncListener {

    private List<Movie> movies;

    public static MovieListFragment newInstance(String movie) {
        MovieListFragment f = new MovieListFragment();

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MovieAPIAsync movieAPIAsync = new MovieAPIAsync(MovieListFragment.this, 's');
        String query = Uri.encode(getMovieName());
        String uri = "http://www.omdbapi.com/?s=" + query;

        movieAPIAsync.execute(uri);

        /*
            String[] values = new String[] { getMovieName(), "B", "C", "D" };

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

            setListAdapter(adapter);
        */
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO IMPLEMENT CLICK

        String item = (String) getListAdapter().getItem(position);
        Log.i("CLICK", item);

        movieSelectedListener.onMovieSelected(item);
    }

    @Override
    public void setResults(ArrayList<Movie> results) {

        ArrayList<String> strings = new ArrayList<String>();

        for(Movie movie:results) {
            strings.add(movie.getTitle());
        }

        String[] values = strings.toArray(new String[strings.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);
    }

    public interface OnMovieSelectedListener {
        public void onMovieSelected(String name);
    }

    OnMovieSelectedListener movieSelectedListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            movieSelectedListener = (OnMovieSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener");
        }
    }


}
