package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.domain.validator.RentalsValidator;

public class RentalsXmlRepository extends XmlRepo<Integer, Rental> {
    public RentalsXmlRepository(){
        super(new RentalsValidator(),"rentals.xml");
        try{
            this.loadContent(Rental.class);}catch (Exception e){e.printStackTrace();}
    }
}
