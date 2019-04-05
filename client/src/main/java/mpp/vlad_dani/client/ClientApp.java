package mpp.vlad_dani.client;

import mpp.vlad_dani.client.service.ClientServiceImpl;
import mpp.vlad_dani.client.service.MovieServiceImpl;
import mpp.vlad_dani.client.service.RentalServiceImpl;
import mpp.vlad_dani.client.service.StatisticServiceImpl;
import mpp.vlad_dani.client.tcp.TcpClient;
import mpp.vlad_dani.client.ui.ClientConsole;
import mpp.vlad_dani.common.services.Config;
import mpp.vlad_dani.common.services.client_service.ClientService;
import mpp.vlad_dani.common.services.movie_service.MovieService;
import mpp.vlad_dani.common.services.rental_service.RentalService;
import mpp.vlad_dani.common.services.statistic_service.StatisticService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());
        TcpClient tcpClient = new TcpClient(Config.SERVER_HOST,
                Config.SERVER_PORT);
        ClientService clientService =
                new ClientServiceImpl(executorService, tcpClient);
        MovieService movieService=
                new MovieServiceImpl(executorService, tcpClient);
        RentalService rentalService=
                new RentalServiceImpl(executorService, tcpClient);
        StatisticService statisticService=
                new StatisticServiceImpl(executorService,tcpClient);
        ClientConsole clientConsole = new ClientConsole(clientService,movieService,rentalService,statisticService);

        clientConsole.runConsole();

        executorService.shutdownNow();

        System.out.println("client - bye");
    }
}
