package mpp.vlad_dani.server.service;

import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.exceptions.ItemAlreadyExistsException;
import mpp.vlad_dani.common.exceptions.ItemDoesNotExistException;
import mpp.vlad_dani.common.services.rental_service.RentalService;
import mpp.vlad_dani.server.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentalServerImpl implements RentalService {
    private ExecutorService executorService;
    private Repository<Integer, Rental> repository;

    public RentalServerImpl(ExecutorService executorService, Repository<Integer, Rental> repository) {
        this.executorService = executorService;
        this.repository = repository;
    }

    @Override
    public Future<Set<Rental>> getAllRentals() {
        return executorService.submit(() -> {
            Iterable<Rental> rentals = repository.findAll();
            return StreamSupport.stream(rentals.spliterator(), false)
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public void addRental(Rental rental) {
        executorService.submit(() -> {
            Optional<Rental> optionalRental = null;
            try {
                optionalRental = repository.save(rental);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            optionalRental.ifPresent(s -> {
                throw new ItemAlreadyExistsException("Rental ID already exists");
            });
        });
    }

    @Override
    public void removeRentalM(Integer movieID) {
        executorService.submit(()->{
           Optional<Rental> optionalRental=null;
           try {
               List<Rental> lst=StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
               Integer index=lst.stream().map(el->{if(!el.getMovie().equals(movieID))return -1;return lst.indexOf(el);})
                       .filter(el->el!=-1).collect(Collectors.toList()).get(0);
               optionalRental=repository.delete(index);
           }catch (Exception e) {
               e.getMessage();
           }
        });
    }

    @Override
    public void removeRentalC(Integer clientID) {
        executorService.submit(()->{
            try {
                List<Rental> lst=StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
                Integer index=lst.stream().map(el->{if(!el.getClient().equals(clientID))return -1;return lst.indexOf(el);})
                        .filter(el->el!=-1).collect(Collectors.toList()).get(0);
                repository.delete(index);
            }catch (Exception e) {
                e.getMessage();
            }
        });
    }

    @Override
    public Future<Rental> getOneRental(Integer id) {
        return executorService.submit(() -> {
            Optional<Rental> optionalRental = repository.findOne(id);
            optionalRental.orElseThrow(() -> new ItemDoesNotExistException("Cannot find id"));
            return optionalRental.get();
        });
    }
}
