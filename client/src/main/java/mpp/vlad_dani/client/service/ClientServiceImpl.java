package mpp.vlad_dani.client.service;

import mpp.vlad_dani.client.tcp.TcpClient;
import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.EntityFactory;
import mpp.vlad_dani.common.services.Message;
import mpp.vlad_dani.common.services.client_service.ClientService;
import mpp.vlad_dani.common.services.client_service.ClientServiceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {
    private ExecutorService executorService;
    private TcpClient tcpClient;


    public ClientServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<Set<Client>> getAllClients() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ClientService.GET_ALL_CLIENTS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ClientServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> clients = Arrays.asList(response.getBody().split(";"));
            return clients.stream().map((client) -> EntityFactory.clientFromFile(Arrays.asList(client.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public void addClient(Client client) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ClientService.ADD_CLIENT)
                    .body(EntityFactory.clientToFile(client))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ClientServiceException(response.getBody());
        });
    }

    @Override
    public void removeClient(Integer id) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ClientService.REMOVE_CLIENT)
                    .body(id.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ClientServiceException(response.getBody());
        });
    }

    @Override
    public void updateClient(Client client) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ClientService.UPDATE_CLIENT)
                    .body(EntityFactory.clientToFile(client))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ClientServiceException(response.getBody());
        });
    }

    @Override
    public Future<Client> getOneClient(Integer id) {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ClientService.GET_ONE_CLIENT)
                    .body(id.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ClientServiceException(response.getBody());
            return EntityFactory.clientFromFile(Arrays.asList(response.getBody().split(",")));
        });
    }

    @Override
    public Future<Set<Client>> filterClients(String name) {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ClientService.FILTER_CLIENTS)
                    .body(name)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ClientServiceException(response.getBody());
            List<String> clients = Arrays.asList(response.getBody().split(";"));
            return clients.stream().map((client) -> EntityFactory.clientFromFile(Arrays.asList(client.split(",")))).collect(Collectors.toSet());
        });
    }
}
