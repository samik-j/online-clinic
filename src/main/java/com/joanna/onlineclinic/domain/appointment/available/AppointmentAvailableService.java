package com.joanna.onlineclinic.domain.appointment.available;

public class AppointmentAvailableService {

    private AppointmentAvailableRepository repository;

    public AppointmentAvailableService(AppointmentAvailableRepository repository) {
        this.repository = repository;
    }
}
