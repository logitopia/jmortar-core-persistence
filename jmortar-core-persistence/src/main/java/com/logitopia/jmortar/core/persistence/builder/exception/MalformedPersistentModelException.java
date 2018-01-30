package com.logitopia.jmortar.core.persistence.builder.exception;

/**
 * An <tt>Exception</tt> that was thrown, indicating that the model didn't have the expected annotations required on it.
 */
public class MalformedPersistentModelException extends Exception {

    /**
     * Default Constructor. Create an exception giving a reason why it has been thrown.
     *
     * @param message The reason why the exception has been thrown.
     */
    public MalformedPersistentModelException(String message) {
        super(message);
    }
}
