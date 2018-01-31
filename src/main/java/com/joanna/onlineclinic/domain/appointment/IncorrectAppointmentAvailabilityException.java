package com.joanna.onlineclinic.domain.appointment;

public class IncorrectAppointmentAvailabilityException extends RuntimeException {
    public IncorrectAppointmentAvailabilityException(String s) {
        super(s);
    }
}
