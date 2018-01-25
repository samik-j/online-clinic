package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.available.AppointmentAvailableRepository;
import com.joanna.onlineclinic.web.appointment.booked.AppointmentBookedCreationResource;
import org.springframework.stereotype.Service;

@Service
public class AppointmentBookedService {

    private AppointmentBookedRepository appointmentBookedRepository;
    private AppointmentAvailableRepository appointmentAvailableRepository;

    public AppointmentBookedService(AppointmentBookedRepository appointmentBookedRepository,
                                    AppointmentAvailableRepository appointmentAvailableRepository) {
        this.appointmentBookedRepository = appointmentBookedRepository;
        this.appointmentAvailableRepository = appointmentAvailableRepository;
    }

    public boolean appointmentExists(AppointmentBookedCreationResource resource) {
        return appointmentBookedRepository.existsByAppointmentDateTimeAndDoctorIdAndPatientId(
                appointmentAvailableRepository.findOne(resource.getAppointmentAvailableId())
                        .getAppointmentDateTime(),
                resource.getDoctorId(), resource.getPatientId());
    }
}
