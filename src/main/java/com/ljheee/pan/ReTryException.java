package com.ljheee.pan;

/**
 */
public class ReTryException extends Exception{

    public ReTryException(String message) {
        super(message);
    }

    public ReTryException(String message, Throwable cause) {
        super(message, cause);
    }
}
