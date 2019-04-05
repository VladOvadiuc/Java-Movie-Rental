package mpp.vlad_dani.common.services.client_service;

import mpp.vlad_dani.common.domain.Client;

import java.util.Set;
import java.util.concurrent.Future;

public interface ClientService {
    String GET_ALL_CLIENTS = "GET_ALL_CLIENTS";
    Future<Set<Client>> getAllClients();

    String ADD_CLIENT = "ADD_CLIENT";
    void addClient(Client client);

    String REMOVE_CLIENT = "REMOVE_CLIENT";
    void removeClient(Integer id);

    String UPDATE_CLIENT = "UPDATE_CLIENT";
    void updateClient(Client client);

    String GET_ONE_CLIENT = "GET_ONE_CLIENT";
    Future<Client> getOneClient(Integer id);

    String FILTER_CLIENTS = "FILTER_CLIENTS";
    Future<Set<Client>> filterClients(String name);
}
