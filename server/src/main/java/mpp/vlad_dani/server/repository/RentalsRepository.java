package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.domain.validator.RentalsValidator;

import java.util.List;

public class RentalsRepository extends AbstractFileRepo<Integer, Rental> {
    public RentalsRepository(){
        super(new RentalsValidator(),"rentals.txt");
    }

    @Override
    public String toFile(Rental entity){return entity.toFile();}

    public Rental createEntity(List<String> list){
        return new Rental(Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), Integer.parseInt(list.get(0)));
    }
}
