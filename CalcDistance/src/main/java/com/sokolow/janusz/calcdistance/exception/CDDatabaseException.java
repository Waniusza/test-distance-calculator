package com.sokolow.janusz.calcdistance.exception;

/**
 *
 * @author Waniusza
 */
public class CDDatabaseException extends Exception{

    public CDDatabaseException() {
        super();
    }
    
    public CDDatabaseException(String msg) {
        super(msg);
    }
    public CDDatabaseException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
