package com.example.martin.moviebrowser;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 08.11.2015.
 */

@EFragment(android.R.layout.simple_list_item_1)
public class MovieListFragment extends ListFragment {

    OnMovieSelectedListener movieSelectedListener;

    @RestService
    MovieDBClient movieDBClient;

    public String getMovieName() {
        return getArguments().getString("movie");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        executeSearchQuery(getMovieName());
    }

    @ItemClick
    public void listItemClicked(String text) {

        //String item = (String) getListAdapter().getItem(position);
        Log.i("CLICK", text);

        movieSelectedListener.onMovieSelected(text);
    }

    @Background
    void executeSearchQuery(String query) {
        MovieList movieList = movieDBClient.getMovieList(query);
        setResults(movieList.getMovies());

    }

    @UiThread
    public void setResults(List<Movie> results) {

        if (results == null) {
            String[] values = new String[]{"Not found"};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);

            return;
        }
        ArrayList<String> strings = new ArrayList<String>();

        for (Movie movie : results) {
            strings.add(movie.getTitle());
        }

        String[] values = strings.toArray(new String[strings.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);
    }

    public interface OnMovieSelectedListener {
        public void onMovieSelected(String name);
    }

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
