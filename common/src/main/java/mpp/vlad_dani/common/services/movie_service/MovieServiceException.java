package mpp.vlad_dani.common.services.movie_service;

public class MovieServiceException extends RuntimeException {
    public MovieServiceException(String message) {
        super(message);
    }

    public MovieServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieServiceException(Throwable cause) {
        super(cause);
    }
}
