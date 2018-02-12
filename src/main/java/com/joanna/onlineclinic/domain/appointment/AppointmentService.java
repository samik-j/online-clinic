package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.web.appointment.AppointmentCreationResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public Appointment addAppointment(long doctorId, AppointmentCreationResource resource) {
        Doctor doctor = doctorRepository.findOne(doctorId);
        Appointment appointment =
                new Appointment(doctor, resource.getDate(), resource.getTime());

        doctor.addAppointment(appointment);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        doctorRepository.save(doctor);

        return savedAppointment;
    }

    public boolean appointmentExists(long doctorId, AppointmentCreationResource resource) {
        return appointmentRepository.existsByDoctorIdAndAndDateAndTime(
                doctorId, resource.getDate(), resource.getTime());
    }

    public boolean appointmentExists(long appointmentId) {
        return appointmentRepository.exists(appointmentId);
    }

    public List<Appointment> findAppointments(long doctorId) {
        return appointmentRepository.findAll(doctorId);
    }

    public List<Appointment> findAvailableAppointments(long doctorId) {
        return appointmentRepository.findAvailable(doctorId, LocalDate.now(), LocalTime.now());
    }

    public boolean isAvailable(long appointmentId) {
        return appointmentRepository.findOne(appointmentId).isAvailable();
    }
}
