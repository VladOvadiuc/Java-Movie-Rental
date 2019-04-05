package mpp.vlad_dani.common.services.client_service;

public class ClientServiceException extends RuntimeException {
    public ClientServiceException(String message) {
        super(message);
    }

    public ClientServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientServiceException(Throwable cause) {
        super(cause);
    }
}
