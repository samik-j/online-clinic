package com.joanna.onlineclinic.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAndDateAndTime(
            @Param("doctorId") long doctorId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId")
    List<Appointment> findAll(@Param("doctorId") long doctorId);

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId AND " +
            "appointment.available = true AND " +
            "appointment.date > :date AND " +
            "appointment.time > :time")
    List<Appointment> findAvailable(
            @Param("doctorId") long doctorId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);
}
