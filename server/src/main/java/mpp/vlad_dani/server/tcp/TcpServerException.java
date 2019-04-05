package mpp.vlad_dani.server.tcp;

public class TcpServerException extends RuntimeException {
    public TcpServerException(String message) {
        super(message);
    }

    public TcpServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TcpServerException(Throwable cause) {
        super(cause);
    }
}
