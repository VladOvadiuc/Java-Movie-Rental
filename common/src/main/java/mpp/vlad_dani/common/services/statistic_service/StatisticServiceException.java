package mpp.vlad_dani.common.services.statistic_service;

public class StatisticServiceException extends RuntimeException {
    public StatisticServiceException(String message) {
        super(message);
    }

    public StatisticServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatisticServiceException(Throwable cause) {
        super(cause);
    }
}
