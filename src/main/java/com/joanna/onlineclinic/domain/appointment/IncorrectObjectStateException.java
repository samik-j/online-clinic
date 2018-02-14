package com.joanna.onlineclinic.domain.appointment;

public class IncorrectObjectStateException extends RuntimeException {
    public IncorrectObjectStateException(String s) {
        super(s);
    }
}
