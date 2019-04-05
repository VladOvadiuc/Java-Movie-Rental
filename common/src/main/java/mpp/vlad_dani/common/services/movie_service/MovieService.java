package mpp.vlad_dani.common.services.movie_service;

import mpp.vlad_dani.common.domain.Movie;

import java.util.Set;
import java.util.concurrent.Future;

public interface MovieService {
    String GET_ALL_MOVIES = "GET_ALL_MOVIES";
    Future<Set<Movie>> getAllMovies();

    String ADD_MOVIE = "ADD_MOVIE";
    void addMovie(Movie movie);

    String REMOVE_MOVIE = "REMOVE_MOVIE";
    void removeMovie(Integer id);

    String UPDATE_MOVIE = "UPDATE_MOVIE";
    void updateMovie(Movie movie);

    String GET_ONE_MOVIE = "GET_ONE_MOVIE";
    Future<Movie> getOneMovie(Integer id);

    String FILTER_MOVIES = "FILTER_MOVIES";
    Future<Set<Movie>> filterMovies(String name);
}
