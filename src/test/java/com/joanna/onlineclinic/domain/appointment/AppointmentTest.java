package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class AppointmentTest {

    @Test
    public void shouldCreateAppointment() {
        // given
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        LocalDateTime appointmentDateTime = LocalDateTime.of(2017, 9, 12, 18, 00);

        // when
        Appointment appointment = new Appointment(doctor, appointmentDateTime);

        // then
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(appointmentDateTime, appointment.getAppointmentDateTime());
    }

}