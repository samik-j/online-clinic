package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
import com.joanna.onlineclinic.web.appointment.booked.AppointmentBookedCreationResource;
import org.springframework.stereotype.Service;

@Service
public class AppointmentBookedService {

    private AppointmentBookedRepository appointmentBookedRepository;
    private AppointmentRepository appointmentRepository;

    public AppointmentBookedService(AppointmentBookedRepository appointmentBookedRepository,
                                    AppointmentRepository appointmentRepository) {
        this.appointmentBookedRepository = appointmentBookedRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public boolean appointmentExists(AppointmentBookedCreationResource resource) {
        Appointment appointment = appointmentRepository.findOne(resource.getAppointmentId());

        return appointmentBookedRepository.existsByAppointmentDateTimeAndDoctorIdAndPatientId(
                appointment.getAppointmentDateTime(), appointment.getDoctor().getId(),
                resource.getPatientId());
    }
}
