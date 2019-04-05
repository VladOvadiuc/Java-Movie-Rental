package mpp.vlad_dani.common.domain.validator;


import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.exceptions.ValidatorException;

import java.util.Optional;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client m) throws ValidatorException {
        ValidatorException exception;
        exception= (m.getId() < 0)?new ValidatorException("The id of a client can't be less than 0"):
                (m.getName().length() < 3)?new ValidatorException("The name of a client can't be less than 3 characters"):
                        (Character.isLowerCase(m.getName().charAt(0)))?new  ValidatorException("The name of a client can't start with lower case"):null;
        Optional.ofNullable(exception).ifPresent((e) -> {throw e;});
    }
}
