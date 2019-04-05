package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.validator.ClientValidator;

import java.util.List;

public class ClientRepository extends AbstractFileRepo<Integer, Client> {
    public ClientRepository(){
        super(new ClientValidator(),"clients.txt");
    }

    @Override
    public String toFile(Client entity){return entity.toFile();}

    public Client createEntity(List<String> list){
        return new Client(Integer.parseInt(list.get(0)),list.get(1));
    }
}
