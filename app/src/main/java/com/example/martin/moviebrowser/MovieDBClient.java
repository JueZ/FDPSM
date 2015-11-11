package com.example.martin.moviebrowser;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = "http://www.omdbapi.com/", converters = {MappingJackson2HttpMessageConverter.class})
public interface MovieDBClient {

    @Get("?t={title}")
    Movie getMovie(String title);

    @Get("?s={searchInput}")
    MovieList getMovieList(String searchInput);

}
