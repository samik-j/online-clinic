package com.joanna.onlineclinic.domain.doctor;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoctorTest {

    @Test
    public void shouldCreateDoctor() {
        // when
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);

        // then
        assertEquals("Frist", doctor.getFirstName());
        assertEquals("Last", doctor.getLastName());
        assertEquals(Specialty.PEDIATRICIAN, doctor.getSpecialty());
        assertTrue(doctor.getAppointmentsAvailable().isEmpty());
        assertTrue(doctor.getAppointmentsBooked().isEmpty());
    }

}