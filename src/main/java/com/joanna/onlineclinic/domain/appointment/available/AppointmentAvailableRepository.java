package com.joanna.onlineclinic.domain.appointment.available;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentAvailableRepository extends JpaRepository<AppointmentAvailable, Long> {

    @Query("SELECT appointment FROM AppointmentAvailable appointment WHERE " +
            "appointment.doctor.id = :doctorId AND " +
            "appointment.appointmentDateTime = :appointmentDateTime")
    AppointmentAvailable findAppointment(
            @Param("doctorId") long doctorId,
            @Param("appointmentDateTime") LocalDateTime appointmentDateTime);
    // mogloby byc z optional i sprawdzac w service isPresent

    boolean existsByDoctorIdAndAndAppointmentDateTime(
            @Param("doctorId") long doctorId,
            @Param("appointmentDateTime") LocalDateTime appointmentDateTime);

    List<AppointmentAvailable> findAllByDoctorId(@Param("doctorId") long doctorId);
}
