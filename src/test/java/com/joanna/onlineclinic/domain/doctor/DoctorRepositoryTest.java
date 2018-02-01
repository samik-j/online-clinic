package com.joanna.onlineclinic.domain.doctor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void shouldFindBySpecialty() {
        // given
        Doctor doctor = new Doctor("First", "Last", Specialty.GYNAECOLOGIST);
        doctorRepository.save(doctor);

        // when
        List<Doctor> found = doctorRepository.findBySpecialty(Specialty.GYNAECOLOGIST);

        // then
        assertEquals(doctor.getSpecialty(), found.get(0).getSpecialty());
    }

    @Test
    public void shouldReturnEmptyListIfNotFoundBySpecialty() {
        // given
        Doctor doctor = new Doctor("First", "Last", Specialty.GYNAECOLOGIST);
        doctorRepository.save(doctor);

        // when
        List<Doctor> found = doctorRepository.findBySpecialty(Specialty.PEDIATRICIAN);

        // then
        assertTrue(found.isEmpty());
    }

}