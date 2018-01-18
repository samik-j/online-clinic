package com.joanna.onlineclinic.web.appointment.available;

import com.joanna.onlineclinic.domain.appointment.available.AppointmentAvailableService;
import com.joanna.onlineclinic.web.ErrorsResource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentAvailableCreationValidator {

    private AppointmentAvailableService appointmentService;

    public AppointmentAvailableCreationValidator(AppointmentAvailableService appointmentService) {
        this.appointmentService = appointmentService;
    }

    ErrorsResource validate(AppointmentAvailableResource resource) {
        List<String> validationErrors = new ArrayList<>();
        
        if (resource.getAppointmentDateTime() == null) {
            validationErrors.add("Appointment date not specified");
        } else if (resource.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            validationErrors.add("Incorrect appointment date");
        }

        return new ErrorsResource(validationErrors);
    }
}
