package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppointmentTest {

    @Test
    public void shouldCreateAppointment() {
        // given
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);

        // when
        Appointment appointment = new Appointment(doctor, date, time);

        // then
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertTrue(appointment.isAvailable());
    }

    @Test
    public void shouldBook() {
        // given
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);
        Appointment appointment = new Appointment(doctor, date, time);

        // when
        appointment.book();

        // then
        assertFalse(appointment.isAvailable());
    }

    @Test
    public void bookShouldThrowExceptionIfIsNotAvailable() {
        // given
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);
        Appointment appointment = new Appointment(doctor, date, time);
        appointment.book();

        // then
        assertThrows(IncorrectAppointmentAvailabilityException.class, () -> appointment.book());
    }
}