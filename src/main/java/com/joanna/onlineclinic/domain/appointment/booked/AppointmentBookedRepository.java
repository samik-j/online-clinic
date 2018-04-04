package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentBookedRepository extends JpaRepository<AppointmentBooked, Long> {

    @Query("SELECT appointmentBooked FROM AppointmentBooked appointmentBooked WHERE " +
            "appointmentBooked.doctor.id = :doctorId ")
    List<AppointmentBooked> findByDoctorId(@Param("doctorId") long doctorId);

    @Query("SELECT appointmentBooked FROM AppointmentBooked appointmentBooked WHERE " +
            "appointmentBooked.patient.id = :patientId " +
            "ORDER BY appointmentBooked.appointment.date DESC, appointmentBooked.appointment.time DESC")
    List<AppointmentBooked> findByPatientId(@Param("patientId")long patientId);

    @Query("SELECT appointmentBooked FROM AppointmentBooked appointmentBooked WHERE " +
            "(appointmentBooked.appointment.date >= :date AND " +
            "appointmentBooked.patient.id = :patientId) " +
            "ORDER BY appointmentBooked.appointment.date DESC, appointmentBooked.appointment.time DESC")
    List<AppointmentBooked> findCurrentByPatientId(@Param("patientId") long patientId, @Param("date") LocalDate date);

    @Query("SELECT appointmentBooked FROM AppointmentBooked appointmentBooked WHERE " +
            "(appointmentBooked.appointment.date < :date AND " +
            "appointmentBooked.patient.id = :patientId) " +
            "ORDER BY appointmentBooked.appointment.date DESC, appointmentBooked.appointment.time DESC")
    List<AppointmentBooked> findPastByPatientId(@Param("patientId") long patientId, @Param("date") LocalDate date);

    @Query("SELECT appointmentBooked FROM AppointmentBooked appointmentBooked WHERE " +
            "(appointmentBooked.appointment.date >= :date AND " +
            "appointmentBooked.doctor.id = :doctorId) " +
            "ORDER BY appointmentBooked.appointment.date DESC, appointmentBooked.appointment.time DESC")
    List<AppointmentBooked> findCurrentByDoctorId(@Param("doctorId") long doctorId, @Param("date") LocalDate date);

    @Query("SELECT appointmentBooked FROM AppointmentBooked appointmentBooked WHERE " +
            "(appointmentBooked.appointment.date < :date AND " +
            "appointmentBooked.doctor.id = :doctorId) " +
            "ORDER BY appointmentBooked.appointment.date DESC, appointmentBooked.appointment.time DESC")
    List<AppointmentBooked> findPastByDoctorId(@Param("doctorId") long doctorId, @Param("date") LocalDate date);

    boolean existsByAppointmentAndPatientId(Appointment appointment, Long patientId);
}
