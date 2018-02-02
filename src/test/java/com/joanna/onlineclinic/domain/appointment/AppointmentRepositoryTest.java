package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppointmentRepositoryTest {

    private long doctor1Id;
    private long doctor2Id;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Before
    public void createDoctors() {
        Doctor doctor1 = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        Doctor doctor2 = new Doctor("First2", "Last2", Specialty.DERMATOLOGIST);

        doctor1Id = doctorRepository.save(doctor1).getId();
        doctor2Id = doctorRepository.save(doctor2).getId();
    }

    @Test
    public void shouldReturnTrueIfAppointmentExists() {
        // given
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);
        Appointment appointment = new Appointment(doctorRepository.findOne(doctor1Id), date, time);

        appointmentRepository.save(appointment);

        // when
        boolean result = appointmentRepository.existsByDoctorIdAndAndDateAndTime(doctor1Id, date, time);
        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfAppointmentDoesNotExist() {
        // given
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);

        // when
        boolean result = appointmentRepository.existsByDoctorIdAndAndDateAndTime(doctor1Id, date, time);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldFindAllByDoctorId() {
        // given
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);
        Appointment appointment1 = new Appointment(doctorRepository.findOne(doctor1Id), date, time);
        Appointment appointment2 = new Appointment(doctorRepository.findOne(doctor2Id), date, time);

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        // when
        List<Appointment> found = appointmentRepository.findAll(doctor1Id);

        // then
        assertEquals(1, found.size());
        assertTrue(found.contains(appointment1));
    }

}