package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.doctor.DoctorRepositoryStub;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.web.appointment.AppointmentResource;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppointmentServiceTest {

    private AppointmentRepository appointmentRepository = new AppointmentRepositoryStub();
    private DoctorRepository doctorRepository = new DoctorRepositoryStub();
    private AppointmentService service =
            new AppointmentService(appointmentRepository, doctorRepository);

    private long doctorId;
    private long appointment1Id;

    @Before
    public void saveEntities() {
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        doctorId = doctorRepository.save(doctor).getId();

        Appointment appointment1 = new Appointment(
                doctor, LocalDate.now().plusDays(2), LocalTime.of(12, 0));
        Appointment appointment2 = new Appointment(
                doctor, LocalDate.now().minusDays(2), LocalTime.of(12, 0));

        doctor.addAppointment(appointment1);
        doctor.addAppointment(appointment2);

        appointment1Id = appointmentRepository.save(appointment1).getId();
        appointmentRepository.save(appointment2);

        doctorRepository.save(doctor);
    }

    @Test
    public void shouldAddAppointment() {
        // given
        AppointmentResource resource = createAppointmentResource(
                doctorId, LocalDate.now().plusDays(1), LocalTime.of(10, 0));

        // when
        Appointment addedAppointment = service.addAppointment(doctorId, resource);

        // then
        assertEquals(doctorId, addedAppointment.getDoctor().getId());
        assertEquals(LocalDate.now().plusDays(1), addedAppointment.getDate());
        assertEquals(LocalTime.of(10, 0), addedAppointment.getTime());
    }

    @Test
    public void shouldReturnTrueIfAppointmentExists() {
        // given
        AppointmentResource resource = createAppointmentResource(
                doctorId, LocalDate.now().plusDays(2), LocalTime.of(12, 0));

        // when
        boolean result = service.appointmentExists(doctorId, resource);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfAppointmentDoesNotExist() {
        // given
        AppointmentResource resource = createAppointmentResource(
                doctorId, LocalDate.now().plusDays(2), LocalTime.of(10, 0));

        // when
        boolean result = service.appointmentExists(doctorId, resource);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueIfAppointmentExistsById() {
        // when
        boolean result = service.appointmentExists(appointment1Id);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfAppointmentDoesNotExistById() {
        // when
        boolean result = service.appointmentExists(0);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldFindAppointmentsByDoctorId() {
        // when
        List<Appointment> result = service.findAppointments(doctorId);

        // then
        assertEquals(2, result.size());
        assertEquals(doctorId, result.get(0).getDoctor().getId());
        assertEquals(LocalDate.now().plusDays(2), result.get(0).getDate());
        assertEquals(LocalTime.of(12, 0), result.get(0).getTime());
        assertEquals(doctorId, result.get(1).getDoctor().getId());
        assertEquals(LocalDate.now().minusDays(2), result.get(1).getDate());
        assertEquals(LocalTime.of(12, 0), result.get(1).getTime());

    }

    @Test
    public void shouldFindAvailableAppointments() {
        // when
        List<Appointment> result = service.findAvailableAppointments(doctorId);

        // then
        assertEquals(1, result.size());
        assertEquals(doctorId, result.get(0).getDoctor().getId());
        assertEquals(LocalDate.now().plusDays(2), result.get(0).getDate());
        assertEquals(LocalTime.of(12, 0), result.get(0).getTime());
    }

    @Test
    public void shouldReturnTrueIfAppointmentIsAvailable() {
        // when
        boolean result = service.isAvailable(appointment1Id);

        // then
        assertTrue(result);
    }

    private AppointmentResource createAppointmentResource(
            long doctorId, LocalDate date, LocalTime time) {
        AppointmentResource resource = new AppointmentResource();

        resource.setDoctorId(doctorId);
        resource.setDate(date);
        resource.setTime(time);

        return resource;
    }
}