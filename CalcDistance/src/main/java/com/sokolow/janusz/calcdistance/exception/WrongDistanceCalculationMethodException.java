package com.sokolow.janusz.calcdistance.exception;

/**
 *
 * @author Waniusza
 */
public class WrongDistanceCalculationMethodException extends Exception{

    public WrongDistanceCalculationMethodException() {
        super("Choosing of this distance calculation method might generate inaccuracy");
    }
}
