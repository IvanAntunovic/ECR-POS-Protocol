/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.protocol;

/**
 *
 * @author A661517
 */
public class UnknownTestException extends Exception
{

    public UnknownTestException(String message) {
        super("Invalid id " + message);
    }

    public UnknownTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTestException(Throwable cause) {
        super(cause);
    }

    public UnknownTestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
