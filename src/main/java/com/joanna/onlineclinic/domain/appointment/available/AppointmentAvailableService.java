package com.joanna.onlineclinic.domain.appointment.available;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.web.appointment.available.AppointmentAvailableResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentAvailableService {

    private AppointmentAvailableRepository appointmentRepository;
    private DoctorRepository doctorRepository;

    public AppointmentAvailableService(AppointmentAvailableRepository appointmentRepository,
                                       DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public AppointmentAvailable addAppointment(long doctorId, AppointmentAvailableResource resource) {
        Doctor doctor = doctorRepository.findOne(doctorId);
        AppointmentAvailable appointment =
                new AppointmentAvailable(doctor, resource.getAppointmentDateTime());

        doctor.addAppointmentAvailable(appointment);
        AppointmentAvailable addedAppointment = appointmentRepository.save(appointment);
        doctorRepository.save(doctor);

        return addedAppointment;
    }

    public boolean appointmentExists(long doctorId, AppointmentAvailableResource resource) {
        return appointmentRepository.existsByDoctorIdAndAndAppointmentDateTime(
                        doctorId, resource.getAppointmentDateTime());
    }

    public List<AppointmentAvailable> findAppointments(long doctorId) {
        return appointmentRepository.findAllByDoctorId(doctorId);
    }
}
