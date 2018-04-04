package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.AbstractRepositoryStub;
import com.joanna.onlineclinic.domain.appointment.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentBookedRepositoryStub
        extends AbstractRepositoryStub<AppointmentBooked> implements AppointmentBookedRepository {

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
                        && appointment.getAppointment().getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentBooked> findPastByDoctorId(long doctorId, LocalDate date) {
        return store.values().stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId
                        && appointment.getAppointment().getDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByAppointmentAndPatientId(Appointment appointment, Long patientId) {
        return store.values().stream()
                .anyMatch(appointmentBooked ->
                        appointmentBooked.getAppointment().equals(appointment)
                                && appointmentBooked.getPatient().getId() == patientId);
    }

}
