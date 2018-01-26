package com.joanna.onlineclinic.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId AND " +
            "appointment.appointmentDateTime = :appointmentDateTime")
    Appointment findAppointment(
            @Param("doctorId") long doctorId,
            @Param("appointmentDateTime") LocalDateTime appointmentDateTime);
    // mogloby byc z optional i sprawdzac w service isPresent

    boolean existsByDoctorIdAndAndAppointmentDateTime(
            @Param("doctorId") long doctorId,
            @Param("appointmentDateTime") LocalDateTime appointmentDateTime);

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId")
    List<Appointment> findAll(@Param("doctorId") long doctorId);

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId AND " +
            "appointment.available = true AND " +
            "appointment.appointmentDateTime > :date")
    List<Appointment> findAvailable(
            @Param("doctorId") long doctorId,
            @Param("date") LocalDateTime date);
}
