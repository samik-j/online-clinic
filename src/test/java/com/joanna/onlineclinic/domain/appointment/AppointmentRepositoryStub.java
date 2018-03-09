package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.AbstractRepositoryStub;
import com.joanna.onlineclinic.domain.doctor.Doctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentRepositoryStub
        extends AbstractRepositoryStub<Appointment> implements AppointmentRepository {


    @Override
    public boolean existsByDoctorIdAndDateAndTime(long doctorId, LocalDate date, LocalTime time) {
        return store.values().stream()
                .anyMatch(appointment -> appointment.getDoctor().getId() == doctorId
                        && appointment.getDate().equals(date)
                        && appointment.getTime().equals(time));
    }

    @Override
    public List<Appointment> findAll(long doctorId) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findAvailable(long doctorId, LocalDate date, LocalTime time) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId
                        && (appointment.getDate().isAfter(LocalDate.now())
                        || appointment.getDate().isEqual(LocalDate.now())
                        && appointment.getTime().isAfter(LocalTime.now())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findAvailableOnDay(long doctorId, LocalDate date) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId
                        && appointment.getDate().isEqual(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findAvailableOnDay(long doctorId, LocalDate date, LocalTime time) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId
                        && appointment.getDate().isEqual(date)
                        && appointment.getTime().isAfter(LocalTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment findByDoctorAndDateAndTime(Doctor doctor, LocalDate date, LocalTime time) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().equals(doctor)
                        && appointment.getDate().equals(date)
                        && appointment.getTime().equals(time))
                .findFirst()
                .orElse(null);
    }
}
