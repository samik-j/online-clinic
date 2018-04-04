package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.ObjectNotFoundException;
import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepositoryStub;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.doctor.DoctorRepositoryStub;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientRepository;
import com.joanna.onlineclinic.domain.patient.PatientRepositoryStub;
import com.joanna.onlineclinic.web.appointment.booked.AppointmentBookedCreationResource;
import com.joanna.onlineclinic.web.appointment.booked.AppointmentBookedStatusChangeResource;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppointmentBookedServiceTest {

    private AppointmentBookedRepository appointmentBookedRepository =
            new AppointmentBookedRepositoryStub();
    private AppointmentRepository appointmentRepository = new AppointmentRepositoryStub();
    private PatientRepository patientRepository = new PatientRepositoryStub();
    private DoctorRepository doctorRepository = new DoctorRepositoryStub();

    private AppointmentBookedService service = new AppointmentBookedService(
            appointmentBookedRepository, appointmentRepository, patientRepository);

    private long doctorId;
    private long patientId;
    private long appointment1Id;
    private long appointment2Id;
    private long appointmentBookedId;

    @Before
    public void saveEntities() {
        Doctor doctor = new Doctor(
                "First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        Patient patient = new Patient.PatientBuilder()
                .firstName("FirstP")
                .lastName("LastP")
                .nhsNumber(null)
                .phoneNumber("7522332233")
                .email("patient@domain.com")
                .build();

        doctorId = doctorRepository.save(doctor).getId();
        patientId = patientRepository.save(patient).getId();
        appointment1Id = saveAppointment(
                doctor, LocalDate.now().plusDays(2), LocalTime.of(12, 0)).getId();
        appointment2Id = saveAppointment(
                doctor, LocalDate.now().plusDays(2), LocalTime.of(14, 0)).getId();

        appointmentBookedId = saveAppointmentBooked(appointment2Id, patient, "Sick").getId();
    }

    @Test
    public void shouldAddAppointment() {
        // given
        AppointmentBookedCreationResource resource =
                createAppointmentBookedCreationResource(appointment1Id, patientId, "Sick");

        // when
        AppointmentBooked addedAppointment = service.addAppointment(resource);

        // then
        assertEquals(doctorId, addedAppointment.getDoctor().getId());
        assertEquals(LocalDate.now().plusDays(2), addedAppointment.getAppointment().getDate());
        assertEquals(LocalTime.of(12, 0), addedAppointment.getAppointment().getTime());
        assertEquals(patientId, addedAppointment.getPatient().getId());
        assertEquals("Sick", addedAppointment.getReason());
        assertEquals(AppointmentBookedStatus.NOT_CONFIRMED, addedAppointment.getStatus());
    }

    @Test
    public void shouldReturnTrueIfAppointmentExists() {
        // given
        AppointmentBookedCreationResource resource =
                createAppointmentBookedCreationResource(appointment2Id, patientId, "Sick");

        // when
        boolean result = service.appointmentBookedExists(resource);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfAppointmentBookedDoesNotExistBecauseWasNotBooked() {
        // given
        AppointmentBookedCreationResource resource =
                createAppointmentBookedCreationResource(appointment1Id, patientId, "Sick");

        // when
        boolean result = service.appointmentBookedExists(resource);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldReturnFalseIfAppointmentBookedDoesNotExistBecauseAppointmentDoesNotExist() {
        // given
        AppointmentBookedCreationResource resource =
                createAppointmentBookedCreationResource(0, patientId, "Sick");

        // when
        boolean result = service.appointmentBookedExists(resource);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldFindByDoctorId() {
        // when
        List<AppointmentBooked> result = service.findByDoctorId(doctorId);

        // then
        assertEquals(1, result.size());
        assertEquals(doctorId, result.get(0).getDoctor().getId());
        assertEquals(LocalDate.now().plusDays(2), result.get(0).getAppointment().getDate());
        assertEquals(LocalTime.of(14, 0), result.get(0).getAppointment().getTime());
        assertEquals(patientId, result.get(0).getPatient().getId());
        assertEquals("Sick", result.get(0).getReason());
    }

    @Test
    public void shouldFindByPatientId() {
        // when
        List<AppointmentBooked> result = service.findByPatientId(patientId);

        // then
        assertEquals(1, result.size());
        assertEquals(doctorId, result.get(0).getDoctor().getId());
        assertEquals(LocalDate.now().plusDays(2), result.get(0).getAppointment().getDate());
        assertEquals(LocalTime.of(14, 0), result.get(0).getAppointment().getTime());
        assertEquals(patientId, result.get(0).getPatient().getId());
        assertEquals("Sick", result.get(0).getReason());
    }

    @Test
    public void shouldChangeStatus() {
        // given
        AppointmentBookedStatusChangeResource resource = new AppointmentBookedStatusChangeResource();
        resource.setStatus(AppointmentBookedStatus.CONFIRMED);

        // when
        AppointmentBooked appointmentChanged =
                service.changeStatus(appointmentBookedId, resource);

        // then
        assertEquals(appointmentBookedId, appointmentChanged.getId());
        assertEquals(AppointmentBookedStatus.CONFIRMED, appointmentChanged.getStatus());
        assertEquals(doctorId, appointmentChanged.getDoctor().getId());
        assertEquals(patientId, appointmentChanged.getPatient().getId());
        assertEquals(LocalDate.now().plusDays(2), appointmentChanged.getAppointment().getDate());
        assertEquals(LocalTime.of(14, 0), appointmentChanged.getAppointment().getTime());
        assertEquals("Sick", appointmentChanged.getReason());
    }

    @Test
    public void shouldChangeStatusToCancelled() {
        // given
        AppointmentBookedStatusChangeResource resource = new AppointmentBookedStatusChangeResource();
        resource.setStatus(AppointmentBookedStatus.CANCELLED);

        // when
        AppointmentBooked appointmentChanged =
                service.changeStatus(appointmentBookedId, resource);

        // then
        assertEquals(appointmentBookedId, appointmentChanged.getId());
        assertEquals(AppointmentBookedStatus.CANCELLED, appointmentChanged.getStatus());
        assertEquals(doctorId, appointmentChanged.getDoctor().getId());
        assertEquals(patientId, appointmentChanged.getPatient().getId());
        assertEquals(LocalDate.now().plusDays(2), appointmentChanged.getAppointment().getDate());
        assertEquals(LocalTime.of(14, 0), appointmentChanged.getAppointment().getTime());
        assertEquals("Sick", appointmentChanged.getReason());
        assertTrue(appointmentRepository.findOne(appointment2Id).isAvailable());
    }

    private Appointment saveAppointment(Doctor doctor, LocalDate date, LocalTime time) {
        Appointment appointment = new Appointment(doctor, date, time);

        doctor.addAppointment(appointment);
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        doctorRepository.save(doctor);

        return appointmentSaved;
    }

    private AppointmentBooked saveAppointmentBooked(
            long appointmentId, Patient patient, String reason) {
        Appointment appointment = appointmentRepository.findOne(appointmentId);
        Doctor doctor = appointment.getDoctor();
        AppointmentBooked appointmentBooked = new AppointmentBooked(
                appointment, patient, reason);

        appointment.book(appointmentBooked);
        doctor.addAppointmentBooked(appointmentBooked);
        patient.addAppointmentBooked(appointmentBooked);

        AppointmentBooked appointmentSaved = appointmentBookedRepository.save(appointmentBooked);
        doctorRepository.save(doctor);
        patientRepository.save(patient);
        appointmentRepository.save(appointment);

        return appointmentSaved;
    }

    private AppointmentBookedCreationResource createAppointmentBookedCreationResource(
            long appointmentId, long patientId, String reason) {
        AppointmentBookedCreationResource resource = new AppointmentBookedCreationResource();

        resource.setAppointmentId(appointmentId);
        resource.setPatientId(patientId);
        resource.setReason(reason);

        return resource;
    }
}