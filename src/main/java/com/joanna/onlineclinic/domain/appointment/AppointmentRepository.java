package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndDateAndTime(
            @Param("doctorId") long doctorId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId " +
            "ORDER BY appointment.date, appointment.time")
    List<Appointment> findAll(@Param("doctorId") long doctorId);

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId AND " +
            "appointment.available = true AND " +
            "(appointment.date > :date OR " +
            "(appointment.date = :date AND appointment.time > :time)) " +
            "ORDER BY appointment.date, appointment.time")
    List<Appointment> findAvailable(
            @Param("doctorId") long doctorId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId AND " +
            "appointment.available = true AND " +
            "appointment.date = :date " +
            "ORDER BY appointment.date, appointment.time")
    List<Appointment> findAvailableOnDay(
            @Param("doctorId") long doctorId,
            @Param("date") LocalDate date);

    @Query("SELECT appointment FROM Appointment appointment WHERE " +
            "appointment.doctor.id = :doctorId AND " +
            "appointment.available = true AND " +
            "(appointment.date = :date AND " +
            "appointment.time > :time)" +
            "ORDER BY appointment.date, appointment.time")
    List<Appointment> findAvailableOnDay(
            @Param("doctorId") long doctorId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);

    Appointment findByDoctorAndDateAndTime(Doctor doctor, LocalDate date, LocalTime time);
}
