package mpp.vlad_dani.client.ui;

import mpp.vlad_dani.common.domain.BaseEntity;
import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.exceptions.ItemAlreadyExistsException;
import mpp.vlad_dani.common.exceptions.ValidatorException;
import mpp.vlad_dani.common.services.client_service.ClientService;
import mpp.vlad_dani.common.services.movie_service.MovieService;
import mpp.vlad_dani.common.services.rental_service.RentalService;
import mpp.vlad_dani.common.services.statistic_service.StatisticService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientConsole {
    private ClientService clientService;
    private MovieService movieService;
    private RentalService rentalService;
    private StatisticService statisticService;

    public ClientConsole(ClientService clientService,MovieService movieService,RentalService rentalService, StatisticService statisticService) {
        this.clientService = clientService;
        this.movieService = movieService;
        this.rentalService=rentalService;
        this.statisticService=statisticService;
    }

    private static void printMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("1. Movie menu.\n");
        menu.append("2. Client menu.\n");
        menu.append("3. Rental menu.\n");
        menu.append("4. Reports menu.\n");
        menu.append("0. Exit\n");
        menu.append("Enter command: ");
        System.out.println(menu);
    }


    public void runConsole() {
        while (true) {
            try {
                printMenu();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(bufferedReader.readLine());
                switch (option) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        movieMenu();
                        break;
                    case 2:
                        clientMenu();
                        break;
                    case 3:
                        rentalMenu();
                        break;
                    case 4:
                        reportsMenu();
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch (IOException | RuntimeException e) {
                System.out.println("Error:\n\t" + e.getMessage());
            }
        }
    }


    private void printMovieMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("1. Get all movies.\n");
        menu.append("2. Get one movie.\n");
        menu.append("3. Add movie.\n");
        menu.append("4. Remove one movie.\n");
        menu.append("5. Update one movie.\n");
        menu.append("6. Filter movies.\n");
        menu.append("0. Exit\n");
        menu.append("Enter command: ");
        System.out.println(menu);
    }

    private void movieMenu() {
        while (true) {
            try {
                printMovieMenu();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(bufferedReader.readLine());
                if (option == 0) {
                    break;
                }
                switch (option) {
                    case 1:
                        printAllMovies();
                        break;
                    case 2:
                        printOneMovie();
                        break;
                    case 3:
                        addMovie();
                        break;
                    case 4:
                        removeMovie();
                        break;
                    case 5:
                        updateMovie();
                        break;
                    case 6:
                        filterMovies();
                        break;

                    default:
                        System.out.println("Invalid option");
                }
            } catch (IOException | RuntimeException e) {
                System.out.println("Error:\n\t" + e.getMessage());
            }
        }
    }

    private void printClientMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("1. Get all clients.\n");
        menu.append("2. Get one client.\n");
        menu.append("3. Add client.\n");
        menu.append("4. Remove one client.\n");
        menu.append("5. Update one client.\n");
        menu.append("6. Filter clients by name.\n");
        menu.append("0. Exit\n");
        menu.append("Enter command: ");
        System.out.println(menu);
    }


    private void clientMenu() {
        while (true) {
            try {
                printClientMenu();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(bufferedReader.readLine());
                if (option == 0) {
                    break;
                }
                switch (option) {
                    case 1:
                        printAllClients();
                        break;
                    case 2:
                        printOneClient();
                        break;
                    case 3:
                        addClient();
                        break;
                    case 4:
                        removeClient();
                        break;
                    case 5:
                        updateClient();
                        break;
                    case 6:
                        filterClients();
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (IOException | RuntimeException e) {
                System.out.println("Error:\n\t" + e.getMessage());
            }
        }
    }


    private void printRentalMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("1. Get all rentals.\n");
        menu.append("2. Get one rental.\n");
        menu.append("3. Add rental.\n");
        menu.append("0. Exit\n");
        menu.append("Enter command: ");
        System.out.println(menu);
    }


    private void rentalMenu() {
        while (true) {
            try {
                printRentalMenu();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(bufferedReader.readLine());
                if (option == 0) {
                    break;
                }
                switch (option) {
                    case 1:
                        printAllRentals();
                        break;
                    case 2:
                        printOneRental();
                        break;
                    case 3:
                        addRental();
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (IOException | RuntimeException e) {
                System.out.println("Error:\n\t" + e.getMessage());
            }
        }
    }


    private void printReportsMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("1. Show the top rented movie(s).\n");
        menu.append("2. Show the most active client(s).\n");
        menu.append("3. Show all the rented movies.\n");
        menu.append("4. Show all the active clients.\n");
        menu.append("0. Exit\n");
        menu.append("Enter command: ");
        System.out.println(menu);
    }


    private void reportsMenu() {
        while (true) {
            try {
                printReportsMenu();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(bufferedReader.readLine());
                if (option == 0) {
                    break;
                }
                switch (option) {
                    case 1:
                        topRentedMovies();
                        break;
                    case 2:
                        mostActiveClients();
                        break;
                    case 3:
                        rentedMovies();
                        break;
                    case 4:
                        activeClients();
                    default:
                        System.out.println("Invalid option");
                }
            } catch (IOException | RuntimeException e) {
                System.out.println("Error:\n\t" + e.getMessage());
            }
        }
    }


    private void addMovie(){
        try {
            Optional<Movie> movie = readMovie();
            movie.orElseThrow(() -> new ValidatorException("Invalid movie input"));
            movieService.addMovie(movie.get());
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addRental(){
        try {
            Optional<Rental> rental = readRental();
            rental.orElseThrow(() -> new ValidatorException("Invalid movie input"));
            rentalService.addRental(rental.get());
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAllMovies(){
        try {
            Future<Set<Movie>> movies = movieService.getAllMovies();
            movies.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void topRentedMovies(){
        try {
            Future<Set<Movie>> movies = statisticService.topRentedMovies();
            movies.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void mostActiveClients(){
        try {
            Future<Set<Client>> movies = statisticService.topActiveClients();
            movies.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void rentedMovies(){
        try {
            Future<Set<Movie>> movies = statisticService.rentedMovies();
            movies.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void activeClients(){
        try {
            Future<Set<Client>> movies = statisticService.activeClients();
            movies.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void printAllRentals(){
        try {
            Future<Set<Rental>> rentals = rentalService.getAllRentals();
            rentals.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void printAllClients() {
        try {
            Future<Set<Client>> clients = clientService.getAllClients();
            clients.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addClient() {
        try {
            Optional<Client> client = readClient();
            client.orElseThrow(() -> new ValidatorException("Invalid client input"));
            clientService.addClient(client.get());
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }


    private static Optional<Client> readClient() {
        System.out.println("Read Client {ID, name}");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferedReader.readLine());
            String name = bufferedReader.readLine();
            Client client = new Client(id,name);
            return Optional.of(client);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private static Optional<Movie> readMovie(){
        System.out.println("Read Movie {ID, title, year, genre, duration, rating}");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferedReader.readLine());
            String title = bufferedReader.readLine();
            Integer year = Integer.valueOf(bufferedReader.readLine());
            String genre=bufferedReader.readLine();
            Integer duration=Integer.valueOf(bufferedReader.readLine());
            Float rating=Float.valueOf(bufferedReader.readLine());
            Movie movie=new Movie(id,title,year,genre,duration,rating);
            return Optional.of(movie);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Integer available(){
        try {
            return rentalService.getAllRentals().get().stream().map(BaseEntity::getId).reduce(-1,(acc,el)->acc=Math.max(acc,el));
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    private boolean movieExists(Integer id){
        try {
            List<Integer> lst = movieService.getAllMovies().get().stream().map(el->{if(el.getId().equals(id))return 1;return -1;})
                    .collect(Collectors.toList());
            return Collections.max(lst).equals(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean clientExists(Integer id){
        try {
            List<Integer> lst = clientService.getAllClients().get().stream().map(el->{if(el.getId().equals(id))return 1;return -1;})
                    .collect(Collectors.toList());
            return Collections.max(lst).equals(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Optional<Rental> readRental(){
        System.out.println("Read Rental {MovieID, ClientID}");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer mid=Integer.valueOf(bufferedReader.readLine());
            Integer cid=Integer.valueOf(bufferedReader.readLine());
            if(!this.movieExists(mid)|| !this.clientExists(cid))return Optional.empty();
            Rental rental=new Rental(mid,cid,this.available()+1);
            return Optional.of(rental);
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void removeMovie(){
        System.out.println("Enter movie ID");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferedReader.readLine());
            rentalService.removeRentalM(id);
            movieService.removeMovie(id);
            System.out.println("Item removed successfully");
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeClient() {
        System.out.println("Enter client ID");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferedReader.readLine());
            rentalService.removeRentalC(id);
            clientService.removeClient(id);
            System.out.println("Item removed successfully");
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateMovie(){
        try {
            Optional<Movie> movie = readMovie();
            movie.orElseThrow(() -> new ValidatorException("Invalid movie input"));
            movieService.updateMovie(movie.get());
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateClient() {
        try {
            Optional<Client> client = readClient();
            client.orElseThrow(() -> new ValidatorException("Invalid client input"));
            clientService.updateClient(client.get());
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printOneMovie(){
        System.out.println("Enter movie ID");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferedReader.readLine());
            Movie movie = movieService.getOneMovie(id).get();

            System.out.println(movie);
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printOneClient() {
        System.out.println("Enter client ID");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferedReader.readLine());
            Client client = clientService.getOneClient(id).get();

            System.out.println(client);
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }

    }

    private void printOneRental(){
        System.out.println("Enter rental ID");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferedReader.readLine());
            Rental rental = rentalService.getOneRental(id).get();

            System.out.println(rental);
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void filterMovies(){
        System.out.println("Enter title: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Future<Set<Movie>> movies = movieService.filterMovies(bufferedReader.readLine());
            movies.get().forEach(System.out::println);
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void filterClients() {
        System.out.println("Enter name: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Future<Set<Client>> clients = clientService.filterClients(bufferedReader.readLine());
            clients.get().forEach(System.out::println);
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    //private void printClientsPagination() {
    //    Scanner scanner = new Scanner(System.in);
    //    System.out.println("Enter page size: ");
    //    int size = scanner.nextInt();
    //    clientService.setSize(size);
//
    //    System.out.println("Enter 'n' - for next; 'x' - for exit: ");
//
    //    while (true) {
    //        String cmd = scanner.next();
    //        if (cmd.equals("x")) {
    //            System.out.println("exit");
    //            break;
    //        }
    //        if (!cmd.equals("n")) {
    //            System.out.println("Invalid Option");
    //            continue;
    //        }
//
    //        Future<Set<Client>> clients = clientService.getNextClients();
    //        try {
    //            if (clients.get().size() == 0) {
    //                System.out.println("No more clients!");
    //                break;
    //            }
    //            clients.get().forEach(System.out::println);
    //        } catch (InterruptedException | ExecutionException e) {
    //            e.printStackTrace();
    //        }
    //    }
//
    //}
}
