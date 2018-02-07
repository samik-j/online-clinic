package com.joanna.onlineclinic.web.appointment;

import com.joanna.onlineclinic.domain.appointment.AppointmentService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
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

        if (resource.getDate() == null) {
            validationErrors.add("Appointment date not specified");
        } else if (resource.getDate().isBefore(LocalDate.now())) {
            validationErrors.add("Incorrect appointment date");
        }
        if (resource.getTime() == null) {
            validationErrors.add("Appointment time not specified");
        } else if (isTimeValid(resource.getDate(), resource.getTime())) {
            validationErrors.add("Incorrect appointment time");
        }
        if (resource.getDate() != null && resource.getTime() != null
                && !isAppointmentUnique(doctorId, resource)) {
            validationErrors.add("Appointment already exists");
        }

        return new ErrorsResource(validationErrors);
    }

    private boolean isAppointmentUnique(long doctorId, AppointmentResource resource) {
        return !appointmentService.appointmentExists(doctorId, resource);
    }

    private boolean isTimeValid(LocalDate date, LocalTime time) {
        return date != null && date.equals(LocalDate.now()) && time.isBefore(LocalTime.now());
    }
}
