package com.joanna.onlineclinic.domain.appointment.booked;


import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientRepository;
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
public class AppointmentBookedRepositoryTest {

    private long doctorId;
    private long patientId;

    @Autowired
    private AppointmentBookedRepository appointmentBookedRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Before
    public void setupDatabase() {
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("5322443322")
                .email("someone@domain.com")
                .build();

        doctorId = doctorRepository.save(doctor).getId();
        patientId = patientRepository.save(patient).getId();
    }

    @Test
    public void shouldReturnTrueIfAppointmentExists() {
        // given
        LocalDate date = LocalDate.of(2018, 7, 12);
        LocalTime time = LocalTime.of(12, 00);
        AppointmentBooked appointment = new AppointmentBooked(
                doctorRepository.findOne(doctorId), date,
                time, patientRepository.findOne(patientId), "Sick");

        appointmentBookedRepository.save(appointment);

        // when
        boolean result = appointmentBookedRepository.existsByDateAndTimeAndDoctorIdAndPatientId(
                date, time, doctorId, patientId);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfAppointmentDoesNotExists() {
        // given
        LocalDate date = LocalDate.of(2018, 7, 12);
        LocalTime time = LocalTime.of(12, 00);

        // when
        boolean result = appointmentBookedRepository.existsByDateAndTimeAndDoctorIdAndPatientId(
                date, time, doctorId, patientId);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldFindByDoctorId() {
        // given
        LocalDate date = LocalDate.of(2018, 7, 12);
        LocalTime time = LocalTime.of(12, 00);
        AppointmentBooked appointment = new AppointmentBooked(
                doctorRepository.findOne(doctorId), date,
                time, patientRepository.findOne(patientId), "Sick");

        appointmentBookedRepository.save(appointment);

        // when
        List<AppointmentBooked> found = appointmentBookedRepository.findByDoctorId(doctorId);

        // then
        assertTrue(found.contains(appointment));
        assertEquals(1, found.size());
    }

    @Test
    public void shouldReturnEmptyListIfNoneMatchInFindByDoctorId() {
        // given
        LocalDate date = LocalDate.of(2018, 7, 12);
        LocalTime time = LocalTime.of(12, 00);
        AppointmentBooked appointment = new AppointmentBooked(
                doctorRepository.findOne(doctorId), date,
                time, patientRepository.findOne(patientId), "Sick");

        appointmentBookedRepository.save(appointment);

        // when
        List<AppointmentBooked> found = appointmentBookedRepository.findByDoctorId(0L);

        // then
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByPatientId() {
        // given
        LocalDate date = LocalDate.of(2018, 7, 12);
        LocalTime time = LocalTime.of(12, 00);
        AppointmentBooked appointment = new AppointmentBooked(
                doctorRepository.findOne(doctorId), date,
                time, patientRepository.findOne(patientId), "Sick");

        appointmentBookedRepository.save(appointment);

        // when
        List<AppointmentBooked> found = appointmentBookedRepository.findByPatientId(patientId);

        // then
        assertTrue(found.contains(appointment));
        assertEquals(1, found.size());
    }

    @Test
    public void shouldReturnEmptyListIfNoneMatchInFindByPatientId() {
        // given
        LocalDate date = LocalDate.of(2018, 7, 12);
        LocalTime time = LocalTime.of(12, 00);
        AppointmentBooked appointment = new AppointmentBooked(
                doctorRepository.findOne(doctorId), date,
                time, patientRepository.findOne(patientId), "Sick");

        appointmentBookedRepository.save(appointment);

        // when
        List<AppointmentBooked> found = appointmentBookedRepository.findByPatientId(0L);

        // then
        assertTrue(found.isEmpty());
    }
}