package com.joanna.onlineclinic.domain.appointment.booked;

import org.springframework.stereotype.Service;

@Service
public class AppointmentBookedService {

    private AppointmentBookedRepository repository;

    public AppointmentBookedService(AppointmentBookedRepository repository) {
        this.repository = repository;
    }
}
