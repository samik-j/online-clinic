package com.joanna.onlineclinic.domain.appointment.booked;


import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
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
    private long appointmentId;

    @Autowired
    private AppointmentBookedRepository appointmentBookedRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Before
    public void setupDatabase() {
        Doctor doctor = new Doctor("First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("5322443322")
                .email("someone@domain.com")
                .build();

        Appointment appointment = new Appointment(
                doctor, LocalDate.of(2018, 7, 12), LocalTime.of(12, 00));

        doctor.addAppointment(appointment);

        doctorId = doctorRepository.save(doctor).getId();
        patientId = patientRepository.save(patient).getId();
        appointmentId = appointmentRepository.save(appointment).getId();
    }

    @Test
    public void shouldReturnTrueIfAppointmentExists() {
        // given
        AppointmentBooked appointment = new AppointmentBooked(
                appointmentRepository.findOne(appointmentId), patientRepository.findOne(patientId), "Sick");

        appointmentBookedRepository.save(appointment);

        // when
        boolean result = appointmentBookedRepository.existsByAppointmentAndPatientId(
                appointmentRepository.findOne(appointmentId), patientId);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfAppointmentDoesNotExists() {
        // when
        boolean result = appointmentBookedRepository.existsByAppointmentAndPatientId(
                appointmentRepository.findOne(appointmentId), patientId);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldFindByDoctorId() {
        // given
        AppointmentBooked appointment = new AppointmentBooked(
                appointmentRepository.findOne(appointmentId), patientRepository.findOne(patientId), "Sick");

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
        AppointmentBooked appointment = new AppointmentBooked(
                appointmentRepository.findOne(appointmentId), patientRepository.findOne(patientId), "Sick");

        appointmentBookedRepository.save(appointment);

        // when
        List<AppointmentBooked> found = appointmentBookedRepository.findByDoctorId(0L);

        // then
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByPatientId() {
        // given
        AppointmentBooked appointment = new AppointmentBooked(
                appointmentRepository.findOne(appointmentId), patientRepository.findOne(patientId), "Sick");

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
        AppointmentBooked appointment = new AppointmentBooked(
                appointmentRepository.findOne(appointmentId), patientRepository.findOne(patientId), "Sick");

        appointmentBookedRepository.save(appointment);

        // when
        List<AppointmentBooked> found = appointmentBookedRepository.findByPatientId(0L);

        // then
        assertTrue(found.isEmpty());
    }
}