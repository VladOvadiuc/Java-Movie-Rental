package mpp.vlad_dani.server.service;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.exceptions.ItemAlreadyExistsException;
import mpp.vlad_dani.common.exceptions.ItemDoesNotExistException;
import mpp.vlad_dani.common.services.client_service.ClientService;
import mpp.vlad_dani.server.repository.DBRepo;
import mpp.vlad_dani.server.repository.Repository;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientServiceImpl implements ClientService {
    private ExecutorService executorService;
    private Repository<Integer,Client> repository;

    public ClientServiceImpl(ExecutorService executorService, Repository<Integer, Client> repository) {
        this.executorService = executorService;
        this.repository = repository;
    }

    @Override
    public Future<Set<Client>> getAllClients() {
        return executorService.submit(() -> {
            Iterable<Client> clients = repository.findAll();
            return StreamSupport.stream(clients.spliterator(), false)
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public void addClient(Client client) {
        executorService.submit(() -> {
            Optional<Client> optionalClient = null;
            try {
                optionalClient = repository.save(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
            optionalClient.ifPresent(s -> {
                throw new ItemAlreadyExistsException("Client ID already exists");
            });
        });
    }

    @Override
    public void removeClient(Integer id) {
        executorService.submit(() -> {
            Optional<Client> clientOption = null;
            try {
                clientOption = repository.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            clientOption.orElseThrow(() -> new ItemDoesNotExistException("Cannot find id"));
        });
    }

    @Override
    public void updateClient(Client client) {
        executorService.submit(() -> {
            Optional<Client> optionalClient = null;
            try {
                optionalClient = repository.update(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
            optionalClient.orElseThrow(() -> new ItemDoesNotExistException("Client ID does not exist"));
        });
    }

    @Override
    public Future<Client> getOneClient(Integer id) {
        return executorService.submit(() -> {
            Optional<Client> clientOption = repository.findOne(id);
            clientOption.orElseThrow(() -> new ItemDoesNotExistException("Cannot find id"));
            return clientOption.get();
        });
    }

    @Override
    public Future<Set<Client>> filterClients(String name) {
        return executorService.submit(() -> {
            Iterable<Client> clients = repository.findAll();
            Set<Client> filteredClients = new HashSet<>();
            clients.forEach(filteredClients::add);
            filteredClients.removeIf(s -> !s.getName().contains(name));
            return filteredClients;
        });
    }
}
