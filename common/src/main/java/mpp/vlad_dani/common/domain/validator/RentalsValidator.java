package mpp.vlad_dani.common.domain.validator;

import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.exceptions.ValidatorException;

import java.util.Optional;

public class RentalsValidator implements Validator<Rental> {
    @Override
    public void validate(Rental rentals) throws ValidatorException {
        ValidatorException exception=(rentals.getClient()<0)? new ValidatorException("Client ID must be greater than 0"):
                (rentals.getMovie()<0)? new ValidatorException("Movie ID must be greater than 0"):null;
        Optional.ofNullable(exception).ifPresent((e) -> {throw e;});
    }
}
