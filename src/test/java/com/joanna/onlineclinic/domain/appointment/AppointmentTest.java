package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.domain.patient.Patient;
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
        Doctor doctor = new Doctor(
                "First", "Last","doctor@domain.com", Specialty.PEDIATRICIAN);
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 0);

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
        Doctor doctor = new Doctor(
                "First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build();
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 0);
        Appointment appointment = new Appointment(doctor, date, time);
        AppointmentBooked appointmentBooked = new AppointmentBooked(appointment, patient, "Sick");

        // when
        appointment.book(appointmentBooked);

        // then
        assertFalse(appointment.isAvailable());
        assertTrue(appointment.getAppointmentsBooked().size() == 1);
        assertTrue(appointment.getAppointmentsBooked().contains(appointmentBooked));
    }

    @Test
    public void bookShouldThrowExceptionIfIsNotAvailable() {
        // given
        Doctor doctor = new Doctor(
                "First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build();
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 0);
        Appointment appointment = new Appointment(doctor, date, time);
        AppointmentBooked appointmentBooked = new AppointmentBooked(appointment, patient, "Sick");

        appointment.book(appointmentBooked);

        // expect
        assertThrows(IncorrectObjectStateException.class, () -> appointment.book(appointmentBooked));
    }

    @Test
    public void shouldCancel() {
        // given
        Doctor doctor = new Doctor(
                "First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build();
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 0);
        Appointment appointment = new Appointment(doctor, date, time);
        AppointmentBooked appointmentBooked = new AppointmentBooked(appointment, patient, "Sick");

        appointment.book(appointmentBooked);

        // when
        appointment.cancel();

        // then
        assertTrue(appointment.isAvailable());
    }

    @Test
    public void cancelShouldThrowExceptionIfIsAvailable() {
        // given
        Doctor doctor = new Doctor(
                "First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 0);
        Appointment appointment = new Appointment(doctor, date, time);

        // expect
        assertThrows(IncorrectObjectStateException.class, appointment::cancel);
    }
}