package calebzhou.rdi.microservice.exception;

/**
 * Created by calebzhou on 2022-09-09,21:11.
 */
public class RdiTokenFailureException extends RuntimeException{
    public RdiTokenFailureException(String msg){
        super(msg);
    }
}
