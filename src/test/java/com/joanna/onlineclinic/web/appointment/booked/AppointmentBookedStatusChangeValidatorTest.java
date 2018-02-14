package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepositoryStub;
import com.joanna.onlineclinic.domain.appointment.booked.*;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.doctor.DoctorRepositoryStub;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientRepository;
import com.joanna.onlineclinic.domain.patient.PatientRepositoryStub;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AppointmentBookedStatusChangeValidatorTest {

    private AppointmentBookedRepository appointmentBookedRepository =
            new AppointmentBookedRepositoryStub();
    private AppointmentRepository appointmentRepository = new AppointmentRepositoryStub();
    private PatientRepository patientRepository = new PatientRepositoryStub();
    private DoctorRepository doctorRepository = new DoctorRepositoryStub();
    private AppointmentBookedService service = new AppointmentBookedService(
            appointmentBookedRepository, appointmentRepository, patientRepository, doctorRepository);

    private AppointmentBookedStatusChangeValidator validator =
            new AppointmentBookedStatusChangeValidator(service);

    private long appointmentBookedId;

    @Before
    public void saveEntities() {
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
        Patient patient = new Patient.PatientBuilder()
                .firstName("FirstP")
                .lastName("LastP")
                .nhsNumber(null)
                .phoneNumber("7522332233")
                .email("patient@domain.com")
                .build();

        doctorRepository.save(doctor).getId();
        patientRepository.save(patient).getId();
        long appointment2Id = saveAppointment(
                doctor, LocalDate.now().plusDays(2), LocalTime.of(14, 0)).getId();

        appointmentBookedId = saveAppointmentBooked(appointment2Id, patient, "Sick").getId();
    }

    @Test
    public void shouldValidateWithNoErrors() {
        // given
        AppointmentBookedStatusChangeResource resource = new AppointmentBookedStatusChangeResource();
        resource.setStatus(AppointmentBookedStatus.CONFIRMED);

        // when
        ErrorsResource result = validator.validate(
                appointmentBookedId, resource);

        // then
        assertTrue(result.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithErrorIfStatusIsTheSame() {
        // given
        AppointmentBookedStatusChangeResource resource = new AppointmentBookedStatusChangeResource();
        resource.setStatus(AppointmentBookedStatus.NOT_CONFIRMED);

        // when
        ErrorsResource result = validator.validate(
                appointmentBookedId, resource);

        // then
        assertTrue(result.getValidationErrors().contains("Status already changed"));
        assertEquals(1, result.getValidationErrors().size());
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
                doctor, appointment.getDate(), appointment.getTime(),
                patient, reason);

        appointment.book();
        doctor.addAppointmentBooked(appointmentBooked);
        patient.addAppointmentBooked(appointmentBooked);

        AppointmentBooked appointmentSaved = appointmentBookedRepository.save(appointmentBooked);
        doctorRepository.save(doctor);
        patientRepository.save(patient);
        appointmentRepository.save(appointment);

        return appointmentSaved;
    }
}