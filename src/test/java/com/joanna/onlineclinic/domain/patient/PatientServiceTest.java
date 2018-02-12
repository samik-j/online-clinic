package com.joanna.onlineclinic.domain.patient;

import com.joanna.onlineclinic.web.patient.PatientResource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientServiceTest {

    private PatientRepository patientRepository = new PatientRepositoryStub();
    private PatientService service = new PatientService(patientRepository);

    private long patientId;

    @Before
    public void savePatient() {
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .email("someone@domain.com")
                .phoneNumber("7522443322")
                .nhsNumber("0123456789")
                .build();

        patientId = patientRepository.save(patient).getId();
    }

    @Test
    public void shouldRegisterPatient() {
        // given
        PatientResource resource = createPatientResource(
                "First", "LastName", null, "7533443344",
                "some@domain.com");

        // when
        Patient registeredPatient = service.registerPatient(resource);

        // then
        assertEquals(resource.getFirstName(), registeredPatient.getFirstName());
        assertEquals(resource.getLastName(), registeredPatient.getLastName());
        assertEquals(resource.getNhsNumber(), registeredPatient.getNhsNumber());
        assertEquals(resource.getEmail(), registeredPatient.getEmail());
        assertEquals(resource.getPhoneNumber(), registeredPatient.getPhoneNumber());
    }

    @Test
    public void shouldReturnTrueIfExistsByNhsNumber() {
        // given
        String nhsNumber = "0123456789";

        // when
        boolean result = service.existsByNhsNumber(nhsNumber);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfDoesNotExistByNhsNumber() {
        // given
        String nhsNumber = "0123456788";

        // when
        boolean result = service.existsByNhsNumber(nhsNumber);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldFindAll() {
        // when
        List<Patient> result = service.findAll();

        // then
        assertEquals(1, result.size());
        assertEquals("First", result.get(0).getFirstName());
        assertEquals("Last", result.get(0).getLastName());
        assertEquals("someone@domain.com", result.get(0).getEmail());
        assertEquals("7522443322", result.get(0).getPhoneNumber());
        assertEquals("0123456789", result.get(0).getNhsNumber());
    }

    @Test
    public void shouldReturnTrueIfPatientExists() {
        // when
        boolean result = service.existsById(patientId);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfPatientDoesNotExist() {
        // given
        long id = 0;

        // when
        boolean result = service.existsById(id);

        // then
        assertFalse(result);
    }

    private PatientResource createPatientResource(
            String firstName, String lastName, String nhsNumber, String phone, String email) {
        PatientResource resource = new PatientResource();

        resource.setFirstName(firstName);
        resource.setLastName(lastName);
        resource.setNhsNumber(nhsNumber);
        resource.setPhoneNumber(phone);
        resource.setEmail(email);

        return resource;
    }
}