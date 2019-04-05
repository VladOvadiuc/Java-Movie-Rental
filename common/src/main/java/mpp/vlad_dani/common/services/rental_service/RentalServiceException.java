package mpp.vlad_dani.common.services.rental_service;

public class RentalServiceException extends RuntimeException {
    public RentalServiceException(String message) {
        super(message);
    }

    public RentalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalServiceException(Throwable cause) {
        super(cause);
    }
}
