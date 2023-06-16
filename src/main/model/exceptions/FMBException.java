package src.main.model.exceptions;

/**
 * An exception thrown by the Fiction Mashup Battle system. May be 
 * extended to offer more specific exception information.
 * 
 * @author Will Hoover
 */
public class FMBException extends Exception {
    
    /**
     * Creates a new FMBException with the specified message.
     * 
     * @param message The message about the exception.
     */
    public FMBException(String message) {
        super(message);
    }
}
