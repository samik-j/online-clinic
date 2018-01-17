package com.joanna.onlineclinic.domain.appointment.booked;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentBookedRepository extends JpaRepository<AppointmentBooked, Long> {
}
