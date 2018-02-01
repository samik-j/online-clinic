package com.joanna.onlineclinic.domain.doctor;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoctorTest {

    @Test
    public void shouldCreateDoctor() {
        // when
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);

        // then
        assertEquals("First", doctor.getFirstName());
        assertEquals("Last", doctor.getLastName());
        assertEquals(Specialty.PEDIATRICIAN, doctor.getSpecialty());
        assertTrue(doctor.getAppointments().isEmpty());
        assertTrue(doctor.getAppointmentsBooked().isEmpty());
    }

}