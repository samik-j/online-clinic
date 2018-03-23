package com.joanna.onlineclinic.domain.doctor;

import com.joanna.onlineclinic.web.doctor.DoctorResource;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DoctorServiceTest {

    private DoctorRepository repository = mock(DoctorRepository.class);

    private DoctorService service = new DoctorService(repository);

    private DoctorResource createDoctorResource(
            String firstName, String lastName, String email, Specialty specialty) {
        DoctorResource resource = new DoctorResource();

        resource.setFirstName(firstName);
        resource.setLastName(lastName);
        resource.setEmail(email);
        resource.setSpecialty(specialty);

        return resource;
    }

    private Doctor createDoctor(DoctorResource resource) {
        return new Doctor(
                resource.getFirstName(), resource.getLastName(), resource.getEmail(), resource.getSpecialty());
    }

    @Test
    public void shouldRegisterDoctor() {
        // given
        DoctorResource resource = createDoctorResource(
                "First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);

        Doctor doctor = createDoctor(resource);

        when(repository.save(doctor)).thenReturn(doctor);

        // when
        Doctor result = service.registerDoctor(resource);

        // then
        assertEquals(resource.getFirstName(), doctor.getFirstName());
        assertEquals(resource.getLastName(), doctor.getLastName());
        assertEquals(resource.getSpecialty(), doctor.getSpecialty());
        assertTrue(doctor.getAppointments().isEmpty());
        assertTrue(doctor.getAppointmentsBooked().isEmpty());
    }

    @Test
    public void shouldFindDoctors() {
        // given
        Doctor doctor1 = new Doctor(
                "First1", "Last1", "doctor1@domain.com", Specialty.PEDIATRICIAN);
        Doctor doctor2 = new Doctor(
                "First2", "Last2", "doctor2@domain.com", Specialty.GYNAECOLOGIST);
        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        when(repository.findAll()).thenReturn(doctors);

        // when
        List<Doctor> result = service.findDoctors();

        // then
        assertEquals(2, result.size());
        assertEquals(doctor1.getFirstName(), result.get(0).getFirstName());
        assertEquals(doctor1.getLastName(), result.get(0).getLastName());
        assertEquals(doctor1.getSpecialty(), result.get(0).getSpecialty());
        assertEquals(doctor1.getAppointments(), result.get(0).getAppointments());
        assertEquals(doctor1.getAppointmentsBooked(), result.get(0).getAppointmentsBooked());
        assertEquals(doctor2.getFirstName(), result.get(1).getFirstName());
        assertEquals(doctor2.getLastName(), result.get(1).getLastName());
        assertEquals(doctor2.getSpecialty(), result.get(1).getSpecialty());
        assertEquals(doctor2.getAppointments(), result.get(1).getAppointments());
        assertEquals(doctor2.getAppointmentsBooked(), result.get(1).getAppointmentsBooked());
    }

    @Test
    public void shouldFindDoctorsBySpecialty() {
        // given
        Doctor doctor1 = new Doctor(
                "First1", "Last1", "doctor1@domain.com", Specialty.PEDIATRICIAN);
        List<Doctor> doctors = Arrays.asList(doctor1);

        when(repository.findBySpecialty(Specialty.PEDIATRICIAN)).thenReturn(doctors);

        // when
        List<Doctor> result = service.findDoctors(Specialty.PEDIATRICIAN);

        // then
        assertEquals(1, result.size());
        assertEquals(doctor1.getFirstName(), result.get(0).getFirstName());
        assertEquals(doctor1.getLastName(), result.get(0).getLastName());
        assertEquals(doctor1.getSpecialty(), result.get(0).getSpecialty());
        assertEquals(doctor1.getAppointments(), result.get(0).getAppointments());
        assertEquals(doctor1.getAppointmentsBooked(), result.get(0).getAppointmentsBooked());
    }

    @Test
    public void shouldFindDoctorById() {
        // given
        Doctor doctor1 = new Doctor(
                "First1", "Last1", "doctor1@domain.com", Specialty.PEDIATRICIAN);

        when(repository.findOne(1L)).thenReturn(doctor1);

        // when
        Doctor result = service.findDoctorById(1L);

        // then
        assertEquals(doctor1.getFirstName(), result.getFirstName());
        assertEquals(doctor1.getLastName(), result.getLastName());
        assertEquals(doctor1.getSpecialty(), result.getSpecialty());
        assertEquals(doctor1.getAppointments(), result.getAppointments());
        assertEquals(doctor1.getAppointmentsBooked(), result.getAppointmentsBooked());
    }

    @Test
    public void doctorExistsShouldReturnTrue() {
        // given
        when(repository.exists(1L)).thenReturn(true);

        // when
        boolean result = service.doctorExists(1L);

        // then
        assertTrue(result);
    }

    @Test
    public void doctorExistsShouldReturnFalse() {
        // given
        when(repository.exists(1L)).thenReturn(false);

        // when
        boolean result = service.doctorExists(1L);

        // then
        assertFalse(result);
    }

    @Test
    public void existsByEmailShouldReturnTrue() {
        // given
        when(repository.existsByEmail("doctor@domain.com")).thenReturn(true);

        // when
        boolean result = service.existsByEmail("doctor@domain.com");

        // then
        assertTrue(result);
    }

    @Test
    public void existsByEmailShouldReturnFalse() {
        // given
        when(repository.existsByEmail("doctor@domain.com")).thenReturn(false);

        // when
        boolean result = service.existsByEmail("doctor@domain.com");

        // then
        assertFalse(result);
    }
}