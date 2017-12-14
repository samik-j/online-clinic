package com.joanna.onlineclinic.domain.appointment.available;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class AppointmentAvailableTest {

    @Test
    public void shouldCreateAppointment() {
        // given
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        LocalDateTime appointmentDateTime = LocalDateTime.of(2017, 9, 12, 18, 00);

        // when
        AppointmentAvailable appointment = new AppointmentAvailable(doctor, appointmentDateTime);

        // then
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(appointmentDateTime, appointment.getAppointmentDateTime());
    }

}