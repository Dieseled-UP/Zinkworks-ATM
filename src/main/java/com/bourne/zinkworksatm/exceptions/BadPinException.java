package com.bourne.zinkworksatm.exceptions;

public class BadPinException extends Exception{
    public BadPinException(String message) {
        super(message);
    }
}
