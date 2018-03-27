package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.AbstractRepositoryStub;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentBookedRepositoryStub
        extends AbstractRepositoryStub<AppointmentBooked> implements AppointmentBookedRepository {

    @Override
    public boolean existsByDateAndTimeAndDoctorIdAndPatientId(
            LocalDate date, LocalTime time, long doctorId, long patientId) {
        return store.values().stream()
                .anyMatch(appointment -> appointment.getDate().equals(date)
                        && appointment.getTime().equals(time)
                        && appointment.getDoctor().getId() == doctorId
                        && appointment.getPatient().getId() == patientId);
    }

    @Override
    public List<AppointmentBooked> findByDoctorId(long doctorId) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentBooked> findByPatientId(long patientId) {
        return store.values().stream()
                .filter(appointment -> appointment.getPatient().getId() == patientId)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentBooked> findCurrentByPatientId(long patientId, LocalDate date) {
        return null;
    }

    @Override
    public List<AppointmentBooked> findPastByPatientId(long patientId, LocalDate date) {
        return null;
    }

    @Override
    public List<AppointmentBooked> findCurrentByDoctorId(long doctorId, LocalDate date) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId
                        && appointment.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentBooked> findPastByDoctorId(long doctorId, LocalDate date) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId
                        && appointment.getDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

}
