package mpp.vlad_dani.server.service;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.exceptions.ItemAlreadyExistsException;
import mpp.vlad_dani.common.exceptions.ItemDoesNotExistException;
import mpp.vlad_dani.common.services.movie_service.MovieService;
import mpp.vlad_dani.server.repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieServerImpl implements MovieService {
    private ExecutorService executorService;
    private Repository<Integer, Movie> repository;

    public MovieServerImpl(ExecutorService executorService, Repository<Integer, Movie> repository) {
        this.executorService = executorService;
        this.repository = repository;
    }

    @Override
    public Future<Set<Movie>> getAllMovies() {
        return executorService.submit(() -> {
            Iterable<Movie> movies = repository.findAll();
            return StreamSupport.stream(movies.spliterator(), false)
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public void addMovie(Movie movie) {
        executorService.submit(() -> {
            Optional<Movie> optionalMovie = null;
            try {
                optionalMovie = repository.save(movie);
            } catch (Exception e) {
                e.getMessage();
            }
            optionalMovie.ifPresent(s -> {
                throw new ItemAlreadyExistsException("Movie ID already exists");
            });
        });
    }

    @Override
    public void removeMovie(Integer id) {
        executorService.submit(() -> {
            Optional<Movie> optionalMovie = null;
            try {
                optionalMovie = repository.delete(id);
            } catch (Exception e) {
                e.getMessage();
            }
            optionalMovie.orElseThrow(() -> new ItemDoesNotExistException("Cannot find id"));
        });
    }

    @Override
    public void updateMovie(Movie movie) {
        executorService.submit(() -> {
            Optional<Movie> optionalMovie = null;
            try {
                optionalMovie = repository.update(movie);
            } catch (Exception e) {
                e.getMessage();
            }
            optionalMovie.orElseThrow(() -> new ItemDoesNotExistException("Movie ID does not exist"));
        });
    }

    @Override
    public Future<Movie> getOneMovie(Integer id) {
        return executorService.submit(() -> {
            Optional<Movie> optionalMovie = repository.findOne(id);
            optionalMovie.orElseThrow(() -> new ItemDoesNotExistException("Cannot find id"));
            return optionalMovie.get();
        });
    }

    @Override
    public Future<Set<Movie>> filterMovies(String name) {
        return executorService.submit(() -> {
            Iterable<Movie> movies = repository.findAll();
            Set<Movie> filteredMovies = new HashSet<>();
            movies.forEach(filteredMovies::add);
            filteredMovies.removeIf(s -> !s.getTitle().contains(name));
            return filteredMovies;
        });
    }
}
