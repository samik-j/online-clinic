package com.joanna.onlineclinic.domain.patient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Before
    public void savePatient() {
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build();
        patientRepository.save(patient);
    }

    @Test
    public void shouldReturnTrueIfExistsByNhsNumber() {
        // when
        boolean result = patientRepository.existsByNhsNumber("1234567890");

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfDoesNotExistByNhsNumber() {
        // when
        boolean result = patientRepository.existsByNhsNumber("1234589000");

        // then
        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueIfExistsByEmail() {
        // when
        boolean result = patientRepository.existsByEmail("fake@gmail.com");

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfDoesNotExistByEmail() {
        // when
        boolean result = patientRepository.existsByEmail("fake222@gmail.com");

        // then
        assertFalse(result);
    }

}