package com.joanna.onlineclinic.web.appointment;

import com.joanna.onlineclinic.domain.appointment.AppointmentService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentCreationValidator {

    private AppointmentService appointmentService;

    public AppointmentCreationValidator(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    ErrorsResource validate(long doctorId, AppointmentResource resource) {
        List<String> validationErrors = new ArrayList<>();

        if (resource.getAppointmentDateTime() == null) {
            validationErrors.add("Appointment date not specified");
        } else {
            if (resource.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
                validationErrors.add("Incorrect appointment date");
            } else if (!isAppointmentUnique(doctorId, resource)) {
                validationErrors.add("Appointment already exists");
            }
        }

        return new ErrorsResource(validationErrors);
    }

    private boolean isAppointmentUnique(long doctorId, AppointmentResource resource) {
        return !appointmentService.appointmentExists(doctorId, resource);
    }
}
