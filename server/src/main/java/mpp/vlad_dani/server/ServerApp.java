package mpp.vlad_dani.server;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.EntityFactory;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.domain.validator.ClientValidator;
import mpp.vlad_dani.common.domain.validator.MovieValidator;
import mpp.vlad_dani.common.domain.validator.RentalsValidator;
import mpp.vlad_dani.common.services.Config;
import mpp.vlad_dani.common.services.Message;
import mpp.vlad_dani.common.services.client_service.ClientService;
import mpp.vlad_dani.common.services.movie_service.MovieService;
import mpp.vlad_dani.common.services.rental_service.RentalService;
import mpp.vlad_dani.common.services.statistic_service.StatisticService;
import mpp.vlad_dani.server.repository.*;
import mpp.vlad_dani.server.repository.DBRepos.ClientDBRepo;
import mpp.vlad_dani.server.repository.DBRepos.MovieDBRepo;
import mpp.vlad_dani.server.repository.DBRepos.RentalDBRepo;
import mpp.vlad_dani.server.service.ClientServiceImpl;
import mpp.vlad_dani.server.service.MovieServerImpl;
import mpp.vlad_dani.server.service.RentalServerImpl;
import mpp.vlad_dani.server.service.StatisticServerImpl;
import mpp.vlad_dani.server.tcp.TcpServer;
import mpp.vlad_dani.server.tcp.TcpServerException;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());

        TcpServer tcpServer = new TcpServer(executorService,
                Config.SERVER_PORT);
        Repository<Integer, Client> clientRepository = new ClientDBRepo(new ClientValidator());
        Repository<Integer, Movie> movieRepository = new MovieDBRepo(new MovieValidator());
        Repository<Integer, Rental> rentalRepository = new RentalDBRepo(new RentalsValidator());

        ClientService clientService = new ClientServiceImpl(executorService, clientRepository);
        MovieService movieService = new MovieServerImpl(executorService,movieRepository);
        RentalService rentalService = new RentalServerImpl(executorService,rentalRepository);
        StatisticService statisticService = new StatisticServerImpl(executorService,movieRepository,clientRepository,rentalRepository);

        tcpServer.addHandler(
                StatisticService.TOP_RENTED_MOVIES,(request)-> {
                    try {
                        Future<Set<Movie>> result =
                                statisticService.topRentedMovies();
                        String responseBody = result.get().stream().map(EntityFactory::movieToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    }catch (InterruptedException | ExecutionException | TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                StatisticService.TOP_ACTIVE_CLIENTS,(request)-> {
                    try {
                        Future<Set<Client>> result =
                                statisticService.topActiveClients();
                        String responseBody = result.get().stream().map(EntityFactory::clientToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    }catch (InterruptedException | ExecutionException | TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                StatisticService.RENTED_MOVIES,(request)-> {
                    try {
                        Future<Set<Movie>> result =
                                statisticService.rentedMovies();
                        String responseBody = result.get().stream().map(EntityFactory::movieToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    }catch (InterruptedException | ExecutionException | TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                StatisticService.ACTIVE_CLIENTS,(request)-> {
                    try {
                        Future<Set<Client>> result =
                                statisticService.activeClients();
                        String responseBody = result.get().stream().map(EntityFactory::clientToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    }catch (InterruptedException | ExecutionException | TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                RentalService.GET_ALL_RENTALS,(request)->{
                    try{
                        Future<Set<Rental>> result=
                                rentalService.getAllRentals();
                        String responseBody=result.get().stream().map(EntityFactory::rentalToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK,responseBody);
                    }catch (InterruptedException | ExecutionException | TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                MovieService.GET_ALL_MOVIES,(request)->{
                    try{
                        Future<Set<Movie>> result=
                                movieService.getAllMovies();
                        String responseBody=result.get().stream().map(EntityFactory::movieToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK,responseBody);
                    }catch (InterruptedException | ExecutionException | TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ClientService.GET_ALL_CLIENTS, (request) -> {
                    try {
                        Future<Set<Client>> result =
                                clientService.getAllClients();
                        String responseBody = result.get().stream().map(EntityFactory::clientToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    } catch (InterruptedException | ExecutionException | TcpServerException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                RentalService.ADD_RENTAL,(request)->{
                    try{
                        rentalService.addRental(EntityFactory.rentalFromFile(Arrays.asList(request.getBody().split(","))));
                        return getMessage(Message.OK,"Rental added successfully");
                    }catch (RuntimeException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                MovieService.ADD_MOVIE,(request)->{
                    try{
                        movieService.addMovie(EntityFactory.movieFromFile(Arrays.asList(request.getBody().split(","))));
                        return getMessage(Message.OK,"Movie added successfully");
                    }catch (RuntimeException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ClientService.ADD_CLIENT, (request) -> {
                    try {
                        clientService.addClient(EntityFactory.clientFromFile(Arrays.asList(request.getBody().split(","))));
                        return getMessage(Message.OK, "Client added Successfully");
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                MovieService.REMOVE_MOVIE,(request)->{
                    try{
                        clientService.removeClient(Integer.parseInt(request.getBody()));
                        return getMessage(Message.OK,"Movie removed successfully");
                    }catch (TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ClientService.REMOVE_CLIENT, (request) -> {
                    try {
                        clientService.removeClient(Integer.parseInt(request.getBody()));
                        return getMessage(Message.OK, "Client removed Successfully");
                    } catch (TcpServerException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                MovieService.UPDATE_MOVIE,(request)->{
                    try{
                        movieService.updateMovie(EntityFactory.movieFromFile(Arrays.asList(request.getBody().split(","))));
                        return getMessage(Message.OK,"Movie updated successfully");
                    }catch (TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ClientService.UPDATE_CLIENT, (request) -> {
                    try {
                        clientService.updateClient(EntityFactory.clientFromFile(Arrays.asList(request.getBody().split(","))));
                        return getMessage(Message.OK, "Client updated Successfully");
                    } catch (TcpServerException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                MovieService.GET_ONE_MOVIE,(request)->{
                    try{
                        Future<Movie> m=movieService.getOneMovie(Integer.parseInt(request.getBody()));
                        return getMessage(Message.OK,EntityFactory.movieToFile(m.get()));
                    }catch (TcpServerException | InterruptedException | ExecutionException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                RentalService.GET_ONE_RENTAL,(request)->{
                    try{
                        Future<Rental> r=rentalService.getOneRental(Integer.parseInt(request.getBody()));
                        return getMessage(Message.OK,EntityFactory.rentalToFile(r.get()));
                    }catch (TcpServerException | InterruptedException | ExecutionException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ClientService.GET_ONE_CLIENT, (request) -> {
                    try {
                        Future<Client> c = clientService.getOneClient(Integer.parseInt(request.getBody()));
                        return getMessage(Message.OK, EntityFactory.clientToFile(c.get()));
                    } catch (TcpServerException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                MovieService.FILTER_MOVIES,(request)->{
                    try{
                        Future<Set<Movie>> result=movieService.filterMovies(request.getBody());
                        String responseBody=result.get().stream().map(EntityFactory::movieToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK,responseBody);
                    }catch (InterruptedException | ExecutionException | TcpServerException e){
                        e.printStackTrace();
                        return getMessage(Message.ERROR,e.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ClientService.FILTER_CLIENTS, (request) -> {
                    try {
                        Future<Set<Client>> result =
                                clientService.filterClients(request.getBody());
                        String responseBody = result.get().stream().map(EntityFactory::clientToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    } catch (InterruptedException | ExecutionException | TcpServerException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.startServer();

        System.out.println("server - bye");
    }

    private static Message getMessage(String header, String body) {
        return Message.builder()
                .header(header)
                .body(body)
                .build();
    }
}
