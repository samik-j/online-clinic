package com.joanna.onlineclinic.domain.appointment.booked;

import org.springframework.data.jpa.repository.JpaRepository;
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

    List<AppointmentBooked> findByDoctorId(@Param("doctorId") long doctorId);
}
