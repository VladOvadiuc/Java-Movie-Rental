package mpp.vlad_dani.common.exceptions;

public class ItemDoesNotExistException extends RuntimeException {
    /**
     * a custom exception that occurs when you want to delete something that does not exist
     * @param msg
     */
    public ItemDoesNotExistException(String msg){
        super(msg);
    }
}
