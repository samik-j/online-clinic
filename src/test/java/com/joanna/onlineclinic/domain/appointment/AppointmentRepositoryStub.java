package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.AbstractRepositoryStub;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentRepositoryStub
        extends AbstractRepositoryStub<Appointment> implements AppointmentRepository {


    @Override
    public boolean existsByDoctorIdAndAndDateAndTime(long doctorId, LocalDate date, LocalTime time) {
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
}
