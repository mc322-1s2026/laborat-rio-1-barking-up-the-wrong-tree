package com.nexus.exception;

public class NexusValidationException extends RuntimeException {

    public static int nofErrors = 0;; //static vai manter o contador por todas as instancias da class
 
    public NexusValidationException(String message) {
        super(message);
        nofErrors++;
    }

    
}