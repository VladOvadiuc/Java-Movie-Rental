package mpp.vlad_dani.common.exceptions;

public class ItemAlreadyExistsException extends RuntimeException {
    /**
     * a custom exception that occurs when you want to store some data that already exists
     * @param msg
     */
    public ItemAlreadyExistsException(String msg){
        super(msg);
    }
}
