package com.joanna.onlineclinic.domain.appointment.booked;

public class AppointmentBookedService {

    private AppointmentBookedRepository repository;

    public AppointmentBookedService(AppointmentBookedRepository repository) {
        this.repository = repository;
    }
}
