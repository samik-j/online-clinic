package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.available.AppointmentAvailableService;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedService;
import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentBookedCreationResourceValidator {

    private AppointmentAvailableService appointmentAvailableService;
    private AppointmentBookedService appointmentBookedService;
    private DoctorService doctorService;
    private PatientService patientService;

    public AppointmentBookedCreationResourceValidator(
            AppointmentAvailableService appointmentAvailableService,
            AppointmentBookedService appointmentBookedService, DoctorService doctorService,
            PatientService patientService) {
        this.appointmentAvailableService = appointmentAvailableService;
        this.appointmentBookedService = appointmentBookedService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    ErrorsResource validate(AppointmentBookedCreationResource resource) {
        List<String> validationErrors = new ArrayList<>();

        if (resource.getAppointmentAvailableId() == null) {
            validationErrors.add("Available appointment id not specified");
        } else if (!appointmentAvailableExists(resource.getAppointmentAvailableId())) {
            validationErrors.add("Appointment not available");
        }
        if (resource.getDoctorId() == null) {
            validationErrors.add("Doctor id not specified");
        } else if (!doctorExists(resource.getDoctorId())) {
            validationErrors.add("Incorrect doctor id");
        }
        if (resource.getPatientId() == null) {
            validationErrors.add("Patient id not specified");
        } else if (!patientExists(resource.getPatientId())) {
            validationErrors.add("Incorrect patient id");
        }
        if (appointmentBookedExists(resource)) {
            validationErrors.add("Appointment already booked");
        }

        return new ErrorsResource(validationErrors);
    }

    private boolean appointmentAvailableExists(long appointmentId) {
        return appointmentAvailableService.appointmentExists(appointmentId);
    }

    private boolean doctorExists(long doctorId) {
        return doctorService.doctorExists(doctorId);
    }

    private boolean patientExists(long patientId) {
        return patientService.patientExists(patientId);
    }

    private boolean appointmentBookedExists(AppointmentBookedCreationResource resource) {
        return appointmentBookedService.appointmentExists(resource);
    }
}
