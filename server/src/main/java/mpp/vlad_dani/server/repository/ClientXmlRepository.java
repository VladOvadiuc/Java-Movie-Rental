package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.validator.ClientValidator;

public class ClientXmlRepository extends XmlRepo<Integer, Client> {
    public ClientXmlRepository(){
        super(new ClientValidator(),"clients.xml");
        try{
            this.loadContent(Client.class);}catch (Exception e){e.printStackTrace();}
    }
}
