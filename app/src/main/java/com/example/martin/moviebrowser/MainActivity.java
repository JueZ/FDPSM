package com.example.martin.moviebrowser;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.options_menu)
public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectedListener {

    @ViewById(R.id.my_toolbar)
    View viewtoolbar;

    @AfterViews
    void afterViews() {
        Toolbar toolbar = (Toolbar) viewtoolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        handleIntent(intent);

        Log.i("INFO", "Create");

    }

    @UiThread
    public void doMySearch(String movie) {
        Log.i("INFO", "Search");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MovieListFragment_ movieListFragment1 = new MovieListFragment_();

        Bundle args = new Bundle();
        args.putString("movie", movie);
        movieListFragment1.setArguments(args);

        fragmentTransaction.replace(R.id.main_frag, movieListFragment1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // SingleTop Application newIntent will enter here
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    // @Receiver(actionname = Intent.ACTION_SEARCH) hat leider das Ivent nicht bekommen, daher haben wir das Event weiterhin ohne Android Annotations implementiert.
    // Alle anderen Events sind mittels Android Annotations umgesetzt.
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void onMovieSelected(String movie) {

        // CALLBACK ON MOVIE SELECTED
        Log.i("CALLBACK", movie);

        MovieDetailFragment_ newFragment1 = new MovieDetailFragment_();

        Bundle args = new Bundle();
        args.putString("movie", movie);
        newFragment1.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_frag, newFragment1);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    // https://github.com/excilys/androidannotations/wiki/Android-Backward-Compatibility
    // generates onkeydown event
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @OptionsItem
    void homeSelected() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
    }

}
