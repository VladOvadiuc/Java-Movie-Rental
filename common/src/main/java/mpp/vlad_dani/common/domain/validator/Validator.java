package mpp.vlad_dani.common.domain.validator;
import mpp.vlad_dani.common.exceptions.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;

}
