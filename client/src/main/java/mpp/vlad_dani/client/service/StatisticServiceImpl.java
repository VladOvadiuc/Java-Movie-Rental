package mpp.vlad_dani.client.service;

import mpp.vlad_dani.client.tcp.TcpClient;
import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.EntityFactory;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.services.Message;
import mpp.vlad_dani.common.services.rental_service.RentalService;
import mpp.vlad_dani.common.services.rental_service.RentalServiceException;
import mpp.vlad_dani.common.services.statistic_service.StatisticService;
import mpp.vlad_dani.common.services.statistic_service.StatisticServiceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class StatisticServiceImpl implements StatisticService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public StatisticServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<Set<Movie>> topRentedMovies() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(StatisticService.TOP_RENTED_MOVIES)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new StatisticServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> statistics = Arrays.asList(response.getBody().split(";"));
            return statistics.stream().map((statistic) -> EntityFactory.movieFromFile(Arrays.asList(statistic.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Client>> topActiveClients() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(StatisticService.TOP_ACTIVE_CLIENTS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new StatisticServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> statistics = Arrays.asList(response.getBody().split(";"));
            return statistics.stream().map((statistic) -> EntityFactory.clientFromFile(Arrays.asList(statistic.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Movie>> rentedMovies() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(StatisticService.RENTED_MOVIES)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new StatisticServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> statistics = Arrays.asList(response.getBody().split(";"));
            return statistics.stream().map((statistic) -> EntityFactory.movieFromFile(Arrays.asList(statistic.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Client>> activeClients() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(StatisticService.ACTIVE_CLIENTS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new StatisticServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> statistics = Arrays.asList(response.getBody().split(";"));
            return statistics.stream().map((statistic) -> EntityFactory.clientFromFile(Arrays.asList(statistic.split(",")))).collect(Collectors.toSet());
        });
    }
}
