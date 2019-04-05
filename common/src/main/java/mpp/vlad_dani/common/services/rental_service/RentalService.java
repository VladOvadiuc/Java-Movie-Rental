package mpp.vlad_dani.common.services.rental_service;

import mpp.vlad_dani.common.domain.Rental;

import java.util.Set;
import java.util.concurrent.Future;

public interface RentalService {
    String GET_ALL_RENTALS = "GET_ALL_RENTALS";
    Future<Set<Rental>> getAllRentals();

    String ADD_RENTAL = "ADD_RENTAL";
    void addRental(Rental rental);

    String REMOVE_RENTAL_M = "REMOVE_RENTAL_M";
    void removeRentalM(Integer movieID);

    String REMOVE_RENTAL_C = "REMOVE_RENTAL_C";
    void removeRentalC(Integer clientID);

    String GET_ONE_RENTAL = "GET_ONE_RENTAL";
    Future<Rental> getOneRental(Integer id);
}
