package com.example.martin.moviebrowser;

import android.graphics.Bitmap;

/**
 * Created by Prakti on 09.11.2015.
 */
public class Movie {

    private String title;
    private String year;
    private String id;
    private String type;
    private String runtime;
    private String poster;
    private Bitmap movieposter;

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Movie() {}

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getPosterBitmap() { return movieposter; }

    public void setPosterBitmap(Bitmap bitmap)  {
        this.movieposter = bitmap;
    }
}
