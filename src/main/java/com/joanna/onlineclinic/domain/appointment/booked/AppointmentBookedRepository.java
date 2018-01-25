package com.joanna.onlineclinic.domain.appointment.booked;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentBookedRepository extends JpaRepository<AppointmentBooked, Long> {

    boolean existsByAppointmentDateTimeAndDoctorIdAndPatientId(
            @Param("appointmentDateTime")LocalDateTime appointmentDateTime,
            @Param("doctorId") long doctorId,
            @Param("patientId") long patientId);
}
