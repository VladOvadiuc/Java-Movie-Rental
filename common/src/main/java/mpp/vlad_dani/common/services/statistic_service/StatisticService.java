package mpp.vlad_dani.common.services.statistic_service;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.Rental;

import java.util.Set;
import java.util.concurrent.Future;

public interface StatisticService {
    String TOP_RENTED_MOVIES = "TOP_RENTED_MOVIES";
    Future<Set<Movie>> topRentedMovies();

    String TOP_ACTIVE_CLIENTS = "TOP_ACTIVE_CLIENTS";
    Future<Set<Client>> topActiveClients();

    String RENTED_MOVIES = "RENTED_MOVIES";
    Future<Set<Movie>> rentedMovies();

    String ACTIVE_CLIENTS = "ACTIVE_CLIENTS";
    Future<Set<Client>> activeClients();
}
