package com.joanna.onlineclinic.domain.appointment.available;

import com.joanna.onlineclinic.web.appointment.available.AppointmentAvailableResource;
import org.springframework.stereotype.Service;

@Service
public class AppointmentAvailableService {

    private AppointmentAvailableRepository repository;

    public AppointmentAvailableService(AppointmentAvailableRepository repository) {
        this.repository = repository;
    }

    public boolean appointmentExists(AppointmentAvailableResource resource) {
        AppointmentAvailable appointment = repository.findAppointment(resource.getDoctorId(), resource.getAppointmentDateTime());

        return appointment != null;
    }
}
