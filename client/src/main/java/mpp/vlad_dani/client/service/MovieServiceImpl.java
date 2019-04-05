package mpp.vlad_dani.client.service;

import mpp.vlad_dani.client.tcp.TcpClient;
import mpp.vlad_dani.common.domain.EntityFactory;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.services.Message;
import mpp.vlad_dani.common.services.client_service.ClientService;
import mpp.vlad_dani.common.services.client_service.ClientServiceException;
import mpp.vlad_dani.common.services.movie_service.MovieService;
import mpp.vlad_dani.common.services.movie_service.MovieServiceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {
    private ExecutorService executorService;
    private TcpClient tcpClient;


    public MovieServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<Set<Movie>> getAllMovies() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(MovieService.GET_ALL_MOVIES)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new MovieServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> movies = Arrays.asList(response.getBody().split(";"));
            return movies.stream().map((movie) -> EntityFactory.movieFromFile(Arrays.asList(movie.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public void addMovie(Movie movie) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(MovieService.ADD_MOVIE)
                    .body(EntityFactory.movieToFile(movie))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new MovieServiceException(response.getBody());
        });
    }

    @Override
    public void removeMovie(Integer id) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(MovieService.REMOVE_MOVIE)
                    .body(id.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new MovieServiceException(response.getBody());
        });
    }

    @Override
    public void updateMovie(Movie movie) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(MovieService.UPDATE_MOVIE)
                    .body(EntityFactory.movieToFile(movie))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new MovieServiceException(response.getBody());
        });
    }

    @Override
    public Future<Movie> getOneMovie(Integer id) {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(MovieService.GET_ONE_MOVIE)
                    .body(id.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new MovieServiceException(response.getBody());
            return EntityFactory.movieFromFile(Arrays.asList(response.getBody().split(",")));
        });
    }

    @Override
    public Future<Set<Movie>> filterMovies(String name) {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(MovieService.FILTER_MOVIES)
                    .body(name)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new MovieServiceException(response.getBody());
            List<String> movies = Arrays.asList(response.getBody().split(";"));
            return movies.stream().map((movie) -> EntityFactory.movieFromFile(Arrays.asList(movie.split(",")))).collect(Collectors.toSet());
        });
    }
}
