package mpp.vlad_dani.client.service;

import mpp.vlad_dani.client.tcp.TcpClient;
import mpp.vlad_dani.common.domain.EntityFactory;
import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.services.Message;
import mpp.vlad_dani.common.services.movie_service.MovieService;
import mpp.vlad_dani.common.services.movie_service.MovieServiceException;
import mpp.vlad_dani.common.services.rental_service.RentalService;
import mpp.vlad_dani.common.services.rental_service.RentalServiceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class RentalServiceImpl implements RentalService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public RentalServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<Set<Rental>> getAllRentals() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(RentalService.GET_ALL_RENTALS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new RentalServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> rentals = Arrays.asList(response.getBody().split(";"));
            return rentals.stream().map((rental) -> EntityFactory.rentalFromFile(Arrays.asList(rental.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public void addRental(Rental rental) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(RentalService.ADD_RENTAL)
                    .body(EntityFactory.rentalToFile(rental))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new RentalServiceException(response.getBody());
        });
    }

    @Override
    public void removeRentalM(Integer movieID) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(RentalService.REMOVE_RENTAL_M)
                    .body(movieID.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new RentalServiceException(response.getBody());
        });
    }

    @Override
    public void removeRentalC(Integer clientID) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(RentalService.REMOVE_RENTAL_C)
                    .body(clientID.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new RentalServiceException(response.getBody());
        });
    }

    @Override
    public Future<Rental> getOneRental(Integer id) {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(RentalService.GET_ONE_RENTAL)
                    .body(id.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new RentalServiceException(response.getBody());
            return EntityFactory.rentalFromFile(Arrays.asList(response.getBody().split(",")));
        });
    }
}
