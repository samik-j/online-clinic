package com.joanna.onlineclinic.web.appointment.booked;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joanna.onlineclinic.OnlineclinicApplication;
import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedRepository;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedStatus;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OnlineclinicApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@Transactional
public class AppointmentBookedControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentBookedRepository appointmentBookedRepository;

    private long patientId;
    private long doctorId;
    private long appointment1Id;
    private long appointment2Id;
    private long appointmentBookedId;

    @Before
    public void setupDatabase() {
        Doctor doctor = new Doctor(
                "First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        Patient patient = new Patient.PatientBuilder()
                .firstName("FirstP")
                .lastName("LastP")
                .nhsNumber("")
                .email("patient@domain.com")
                .phoneNumber("7522332233")
                .build();

        doctorId = doctorRepository.save(doctor).getId();
        patientId = patientRepository.save(patient).getId();

        appointment1Id = saveAppointment(
                doctor, LocalDate.now().plusDays(5), LocalTime.of(15, 0)).getId();
        appointment2Id = saveAppointment(
                doctor, LocalDate.now().plusDays(5), LocalTime.of(12, 0)).getId();

        appointmentBookedId = saveAppointmentBooked(appointment2Id, patient, "Sick").getId();
    }

    @Test
    public void shouldGetAppointmentsByDoctorIdSuccess() throws Exception {
        // when
        ResultActions result = mockMvc.perform(
                get("/appointmentsBooked?doctorId=" + doctorId));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].doctorId", is((int) doctorId)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$[0].time", is(LocalTime.of(12, 0).toString())))
                .andExpect(jsonPath("$[0].patientId", is((int) patientId)))
                .andExpect(jsonPath("$[0].reason", is("Sick")))
                .andExpect(jsonPath("$[0].status",
                        is(AppointmentBookedStatus.NOT_CONFIRMED.toString())));
    }

    @Test
    public void shouldGetAppointmentsByPatientIdSuccess() throws Exception {
        // when
        ResultActions result = mockMvc.perform(
                get("/appointmentsBooked?patientId=" + patientId));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].doctorId", is((int) doctorId)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$[0].time", is(LocalTime.of(12, 0).toString())))
                .andExpect(jsonPath("$[0].patientId", is((int) patientId)))
                .andExpect(jsonPath("$[0].reason", is("Sick")))
                .andExpect(jsonPath("$[0].status",
                        is(AppointmentBookedStatus.NOT_CONFIRMED.toString())));
    }

    @Test
    public void shouldAddAppointmentSuccess200Ok() throws Exception {
        // given
        AppointmentBookedCreationResource resource =
                createAppointmentBookedCreationResource(appointment1Id, patientId, "Sick");

        // when
        ResultActions result = mockMvc.perform(post("/appointmentsBooked")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resource)));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.doctorId", is((int) doctorId)))
                .andExpect(jsonPath("$.date", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$.time", is(LocalTime.of(15, 0).toString())))
                .andExpect(jsonPath("$.patientId", is((int) patientId)))
                .andExpect(jsonPath("$.reason", is("Sick")))
                .andExpect(jsonPath("$.status",
                        is(AppointmentBookedStatus.NOT_CONFIRMED.toString())));
    }

    @Test
    public void shouldAddAppointmentFailIfResourceValidationFails400BadRequest() throws Exception {
        // given
        AppointmentBookedCreationResource resource =
                createAppointmentBookedCreationResource(appointment1Id, 0, "Sick");

        // when
        ResultActions result = mockMvc.perform(post("/appointmentsBooked")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resource)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.validationErrors",
                        is(Arrays.asList("Incorrect patient id"))));
    }

    @Test
    public void shouldAddAppointmentFailIfResourceValidationFailsAppointmentWasBooked400BadRequest()
            throws Exception {
        // given
        AppointmentBookedCreationResource resource =
                createAppointmentBookedCreationResource(appointment2Id, patientId, "Sick");

        // when
        ResultActions result = mockMvc.perform(post("/appointmentsBooked")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resource)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.validationErrors",
                        is(Arrays.asList(
                                "Appointment not available", "Appointment already booked"))));
    }

    @Test
    public void shouldChangeStatusSuccess200Ok() throws Exception {
        // given
        AppointmentBookedStatusChangeResource resource = new AppointmentBookedStatusChangeResource();
        resource.setStatus(AppointmentBookedStatus.CONFIRMED);

        // when
        ResultActions result = mockMvc.perform(
                put("/appointmentsBooked/{appointmentId}?action=changeStatus",
                        appointmentBookedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.doctorId", is((int) doctorId)))
                .andExpect(jsonPath("$.date", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$.time", is(LocalTime.of(12, 0).toString())))
                .andExpect(jsonPath("$.patientId", is((int) patientId)))
                .andExpect(jsonPath("$.reason", is("Sick")))
                .andExpect(jsonPath("$.status",
                        is(AppointmentBookedStatus.CONFIRMED.toString())));
    }

    @Test
    public void shouldChangeStatusFailIfAppointmentWasNotFound404NotFound() throws Exception {
        // given
        AppointmentBookedStatusChangeResource resource = new AppointmentBookedStatusChangeResource();
        resource.setStatus(AppointmentBookedStatus.CONFIRMED);

        // when
        ResultActions result = mockMvc.perform(
                put("/appointmentsBooked/{appointmentId}?action=changeStatus", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    public void shouldChangeStatusFailIfResourceValidationFails400BadRequest() throws Exception {
        // given
        AppointmentBookedStatusChangeResource resource = new AppointmentBookedStatusChangeResource();
        resource.setStatus(AppointmentBookedStatus.NOT_CONFIRMED);

        // when
        ResultActions result = mockMvc.perform(
                put("/appointmentsBooked/{appointmentId}?action=changeStatus",
                        appointmentBookedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.validationErrors",
                        is(Arrays.asList("Status already changed"))));
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

    private Appointment saveAppointment(Doctor doctor, LocalDate date, LocalTime time) {
        Appointment appointment = new Appointment(
                doctor, date, time);

        doctor.addAppointment(appointment);
        Appointment appointmentSaved = appointmentRepository.save(appointment);
        doctorRepository.save(doctor);

        return appointmentSaved;
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule()).writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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