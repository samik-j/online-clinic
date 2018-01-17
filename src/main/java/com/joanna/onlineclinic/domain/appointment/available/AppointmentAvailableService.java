package com.joanna.onlineclinic.domain.appointment.available;

import org.springframework.stereotype.Service;

@Service
public class AppointmentAvailableService {

    private AppointmentAvailableRepository repository;

    public AppointmentAvailableService(AppointmentAvailableRepository repository) {
        this.repository = repository;
    }
}
