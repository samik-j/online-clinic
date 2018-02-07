package com.joanna.onlineclinic.domain.patient;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientTest {

    @Test
    public void shouldCreatePatient() {
        // when
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build();

        // then
        assertEquals("First", patient.getFirstName());
        assertEquals("Last", patient.getLastName());
        assertEquals("1234567890", patient.getNhsNumber());
        assertEquals("07522222222", patient.getPhoneNumber());
        assertEquals("fake@gmail.com", patient.getEmail());
        assertTrue(patient.getAppointments().isEmpty());
    }

    @Test
    public void createPatientShouldThrowExceptionIfFirstNameIsNotSpecified() {
        assertThrows(NullPointerException.class, () -> new Patient.PatientBuilder()
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build());
    }

    @Test
    public void createPatientShouldThrowExceptionIfFirstNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Patient.PatientBuilder()
                .firstName(" ")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build());
    }

    @Test
    public void createPatientShouldThrowExceptionIfLastNameIsNotSpecified() {
        assertThrows(NullPointerException.class, () -> new Patient.PatientBuilder()
                .firstName("First")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build());
    }

    @Test
    public void createPatientShouldThrowExceptionIfLastNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Patient.PatientBuilder()
                .firstName("First")
                .lastName(" ")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build());
    }

    @Test
    public void createPatientShouldThrowExceptionIfPhoneNumberIsNotSpecified() {
        assertThrows(NullPointerException.class, () -> new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .email("fake@gmail.com")
                .build());
    }

    @Test
    public void createPatientShouldThrowExceptionIfPhoneNumberIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("")
                .email("fake@gmail.com")
                .build());
    }


    @Test
    public void createPatientShouldThrowExceptionIfEmailIsNotSpecified() {
        assertThrows(NullPointerException.class, () -> new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .build());
    }

    @Test
    public void createPatientShouldThrowExceptionIfEmailIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email(" ")
                .build());
    }
}