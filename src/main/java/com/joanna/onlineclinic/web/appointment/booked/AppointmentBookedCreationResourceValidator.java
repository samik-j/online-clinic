package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.AppointmentService;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedService;
import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentBookedCreationResourceValidator {

    private AppointmentService appointmentService;
    private AppointmentBookedService appointmentBookedService;
    private PatientService patientService;

    public AppointmentBookedCreationResourceValidator(
            AppointmentService appointmentService,
            AppointmentBookedService appointmentBookedService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.appointmentBookedService = appointmentBookedService;
        this.patientService = patientService;
    }

    ErrorsResource validate(AppointmentBookedCreationResource resource) {
        List<String> validationErrors = new ArrayList<>();

        if (resource.getAppointmentId() == null) {
            validationErrors.add("Available appointment id not specified");
        } else {
            if (!appointmentExists(resource.getAppointmentId())) {
                validationErrors.add("Appointment not available");
            } else if (!isAppointmentAvailable(resource.getAppointmentId())) {
                validationErrors.add("Appointment not available");
            }
        }
        if (resource.getPatientId() == null) {
            validationErrors.add("Patient id not specified");
        } else if (!patientExists(resource.getPatientId())) {
            validationErrors.add("Incorrect patient id");
        }
        if (appointmentBookedExists(resource)) {
            validationErrors.add("Appointment already booked");
        }
        if (resource.getReason().length() > 250) {
            validationErrors.add("Exceeded maximum number of characters for reason");
        }

        return new ErrorsResource(validationErrors);
    }

    private boolean appointmentExists(long appointmentId) {
        return appointmentService.appointmentExists(appointmentId);
    }

    private boolean isAppointmentAvailable(long appointmentId) {
        return appointmentService.isAvailable(appointmentId);
    }

    private boolean patientExists(long patientId) {
        return patientService.patientExists(patientId);
    }

    private boolean appointmentBookedExists(AppointmentBookedCreationResource resource) {
        return appointmentBookedService.appointmentExists(resource);
    }
}
