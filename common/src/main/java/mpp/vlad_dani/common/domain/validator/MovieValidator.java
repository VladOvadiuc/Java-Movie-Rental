package mpp.vlad_dani.common.domain.validator;

import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.exceptions.ValidatorException;

import java.util.Optional;

public class MovieValidator implements Validator<Movie> {
    @Override
    public void validate(Movie m) throws ValidatorException {
        Optional.ofNullable(m.getTitle()).orElseThrow(() -> new ValidatorException("Title can't be null"));
        Optional.ofNullable(m.getDuration()).orElseThrow(() -> new ValidatorException("Duration can't be null"));
        Optional.ofNullable(m.getGenre()).orElseThrow(() -> new ValidatorException("Genre can't be null"));

        ValidatorException exception=(m.getId()<0)?new  ValidatorException("The id of a movie can't be less than 0"):
                (m.getTitle().length()<3)?new  ValidatorException("The title of a movie can't be less than 3 characters"):
                        (Character.isLowerCase(m.getTitle().charAt(0)))?new  ValidatorException("The title of a movie can't start with lower case"):
                                (m.getYear()<1878)? new  ValidatorException("The first movie was produced in 1878, not earlier"):null;
        Optional.ofNullable(exception).ifPresent((e) -> {throw e;});
    }
}
