package com.joanna.onlineclinic.domain.appointment.booked;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentBookedRepository extends JpaRepository<AppointmentBooked, Long> {

    boolean existsByDateAndTimeAndDoctorIdAndPatientId(
            @Param("date") LocalDate date,
            @Param("time") LocalTime time,
            @Param("doctorId") long doctorId,
            @Param("patientId") long patientId);

    List<AppointmentBooked> findByDoctorId(long doctorId);

    @Query("SELECT appointment FROM AppointmentBooked appointment WHERE " +
            "appointment.patient.id = :patientId " +
            "ORDER BY appointment.date DESC, appointment.time DESC")
    List<AppointmentBooked> findByPatientId(@Param("patientId")long patientId);

    @Query("SELECT appointment FROM AppointmentBooked appointment WHERE " +
            "(appointment.date >= :date AND " +
            "appointment.patient.id = :patientId) " +
            "ORDER BY appointment.date DESC, appointment.time DESC")
    List<AppointmentBooked> findCurrentByPatientId(@Param("patientId") long patientId, @Param("date") LocalDate date);

    @Query("SELECT appointment FROM AppointmentBooked appointment WHERE " +
            "(appointment.date < :date AND " +
            "appointment.patient.id = :patientId) " +
            "ORDER BY appointment.date DESC, appointment.time DESC")
    List<AppointmentBooked> findPastByPatientId(@Param("patientId") long patientId, @Param("date") LocalDate date);

    @Query("SELECT appointment FROM AppointmentBooked appointment WHERE " +
            "(appointment.date >= :date AND " +
            "appointment.doctor.id = :doctorId) " +
            "ORDER BY appointment.date DESC, appointment.time DESC")
    List<AppointmentBooked> findCurrentByDoctorId(@Param("doctorId") long doctorId, @Param("date") LocalDate date);

    @Query("SELECT appointment FROM AppointmentBooked appointment WHERE " +
            "(appointment.date < :date AND " +
            "appointment.doctor.id = :doctorId) " +
            "ORDER BY appointment.date DESC, appointment.time DESC")
    List<AppointmentBooked> findPastByDoctorId(@Param("doctorId") long doctorId, @Param("date") LocalDate date);

}
