package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.domain.patient.Patient;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class AppointmentBookedTest {

    @Test
    public void shouldCreateAppointment() {
        // given
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);
        Patient patient = new Patient("First", "Last", "1234567890", "07522222222", "fake@gmail.com");

        // when
        AppointmentBooked appointment = new AppointmentBooked(doctor, date, time, patient, "Sick");

        // then
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertEquals(patient, appointment.getPatient());
        assertEquals("Sick", appointment.getReason());
        assertEquals(AppointmentBookedStatus.NOT_CONFIRMED, appointment.getStatus());
    }
}