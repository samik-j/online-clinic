package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.web.appointment.AppointmentResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Appointment addAppointment(long doctorId, AppointmentResource resource) {
        Doctor doctor = doctorRepository.findOne(doctorId);
        Appointment appointment =
                new Appointment(doctor, resource.getAppointmentDateTime());

        doctor.addAppointment(appointment);
        Appointment addedAppointment = appointmentRepository.save(appointment);
        doctorRepository.save(doctor);

        return addedAppointment;
    }

    public boolean appointmentExists(long doctorId, AppointmentResource resource) {
        return appointmentRepository.existsByDoctorIdAndAndAppointmentDateTime(
                doctorId, resource.getAppointmentDateTime());
    }

    public boolean appointmentExists(long appointmentId) {
        return appointmentRepository.exists(appointmentId);
    }

    public List<Appointment> findAppointments(long doctorId) {
        return appointmentRepository.findAll(doctorId, LocalDateTime.now());
    }
}
