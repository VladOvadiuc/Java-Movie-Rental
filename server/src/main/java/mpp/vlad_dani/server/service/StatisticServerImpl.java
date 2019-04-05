package mpp.vlad_dani.server.service;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.services.statistic_service.StatisticService;
import mpp.vlad_dani.server.repository.Repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StatisticServerImpl implements StatisticService {
    private ExecutorService executorService;
    private Repository<Integer, Movie> movies;
    private Repository<Integer, Client> clients;
    private Repository<Integer, Rental> rentals;

    public StatisticServerImpl(ExecutorService executorService, Repository<Integer, Movie> movies, Repository<Integer, Client> clients, Repository<Integer, Rental> rentals) {
        this.executorService = executorService;
        this.movies = movies;
        this.clients = clients;
        this.rentals = rentals;
    }

    public List<Rental> rentals(){
        return StreamSupport.stream(this.rentals.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private int withClientID(Integer id){
        return rentals().stream().filter(el-> el.getClient().equals(id)).collect(Collectors.toList()).size();
    }

    private int withMovieID(Integer id){
        return rentals().stream().filter(el-> el.getMovie().equals(id)).collect(Collectors.toList()).size();
    }

    @Override
    public Future<Set<Movie>> topRentedMovies() {
        return executorService.submit(()->{
            Integer max=rentals().stream().map(el->withMovieID(el.getMovie())).reduce(-1,(acc,el)->acc=Math.max(acc,el));
            return rentals().stream().filter(el->withMovieID(el.getMovie())==max).map(Rental::getMovie).collect(Collectors.toSet()).stream().map(el->movies.findOne(el).get()).collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Client>> topActiveClients() {
        return executorService.submit(()->{
            Integer max=rentals().stream().map(el->withClientID(el.getClient())).reduce(-1,(acc,el)->acc=Math.max(acc,el));
            return rentals().stream().filter(el->withClientID(el.getClient())==max).map(Rental::getClient).collect(Collectors.toSet()).stream().map(el->clients.findOne(el).get()).collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Movie>> rentedMovies() {
        return executorService.submit(()->{
            List<Integer> mo=this.rentals().stream().map(el->el.getClient()).collect(Collectors.toList());
            Set<Integer> m=new LinkedHashSet<>();m.addAll(mo);mo.clear();mo.addAll(m);
            return mo.stream().map(el->movies.findOne(el).get()).collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Client>> activeClients() {
        return executorService.submit(()->{
            List<Integer> cl=this.rentals().stream().map(Rental::getClient).collect(Collectors.toList());
            Set<Client> c=new LinkedHashSet<>();
            c.addAll(cl.stream().map(el->clients.findOne(el).get()).collect(Collectors.toSet())); return c;
        });
    }
}
