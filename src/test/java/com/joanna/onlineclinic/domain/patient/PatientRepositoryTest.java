package com.joanna.onlineclinic.domain.patient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void shouldReturnTrueIfExistsByNhsNumber() {
        // given
        Patient patient = new Patient("First", "Last", "1234567890", "07522222222", "fake@gmail.com");
        patientRepository.save(patient);

        // when
        boolean result = patientRepository.existsByNhsNumber("1234567890");

        // then
        assertTrue(result);
    }

}