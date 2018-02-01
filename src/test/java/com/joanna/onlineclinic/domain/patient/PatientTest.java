package com.joanna.onlineclinic.domain.patient;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientTest {

    @Test
    public void shouldCreatePatient() {
        // when
        Patient patient = new Patient("First", "Last", "1234567890", "07522222222", "fake@gmail.com");

        // then
        assertEquals("First", patient.getFirstName());
        assertEquals("Last", patient.getLastName());
        assertEquals("1234567890", patient.getNhsNumber());
        assertEquals("07522222222", patient.getPhoneNumber());
        assertEquals("fake@gmail.com", patient.getEmail());
        assertTrue(patient.getAppointments().isEmpty());
    }
}