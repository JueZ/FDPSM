package com.example.martin.moviebrowser;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.rest.RestService;

import java.net.URL;

/**
 * Created by Martin on 08.11.2015.
 */
@EFragment(R.layout.fragment_moviedetail)
public class MovieDetailFragment extends Fragment {

    @RestService
    MovieDBClient movieDBClient;

    @ViewById(R.id.textViewRuntime)
    TextView textRunTime;

    @ViewById(R.id.textViewTitle)
    TextView textTitle;

    @ViewById(R.id.textViewYearOfRelease)
    TextView textYear;

    @ViewById(R.id.imageView)
    ImageView imageViewPoster;
    private Movie movie;

    @DrawableRes(R.drawable.ic_not_found)
    Drawable bitmapdrawable;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        executeSearchQuery((getMovieName()));
    }

    public String getMovieName() {
        return getArguments().getString("movie");
    }

    @Background
    void executeSearchQuery(String query) {
        Movie movie = movieDBClient.getMovie(query);

        Bitmap bitmap = null;
        URL url = null;
        try {
            url = new URL(movie.getPoster());
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            movie.setPosterBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        setResults(movie);

    }

    @UiThread
    void setResults(Movie movie) {

        this.movie = movie;

        if (textTitle != null) {
            textTitle.setText(movie.getTitle());
        }

        if (textRunTime != null) {
            textRunTime.setText(movie.getRuntime());
        }

        if (textYear != null) {
            textYear.setText(movie.getYear());
        }

        if (imageViewPoster != null) {
            imageViewPoster.setImageBitmap(movie.getPosterBitmap());
        }
    }
}
