package com.joanna.onlineclinic.web.appointment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joanna.onlineclinic.OnlineclinicApplication;
import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.doctor.Specialty;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    private long doctor1Id;
    private long doctor2Id;

    @Before
    public void setupDatabase() {
        Doctor doctor1 = new Doctor(
                "First", "Last", "doctor1@domain.com", Specialty.PEDIATRICIAN);
        Doctor doctor2 = new Doctor(
                "First", "Last", "doctor2@domain.com", Specialty.PEDIATRICIAN);

        doctor1Id = doctorRepository.save(doctor1).getId();
        doctor2Id = doctorRepository.save(doctor2).getId();

        Appointment appointment1 = new Appointment(
                doctor1, LocalDate.now().plusDays(5), LocalTime.of(15, 0));
        Appointment appointment2 = new Appointment(
                doctor2, LocalDate.now().minusDays(1), LocalTime.of(15, 0));
        Appointment appointment3 = new Appointment(
                doctor2, LocalDate.now().plusDays(5), LocalTime.of(15, 0));

        doctor1.addAppointment(appointment1);
        appointmentRepository.save(appointment1);
        doctorRepository.save(doctor1);

        doctor2.addAppointment(appointment2);
        appointmentRepository.save(appointment2);
        doctorRepository.save(doctor2);

        doctor2.addAppointment(appointment3);
        appointmentRepository.save(appointment3);
        doctorRepository.save(doctor2);
    }

    @After
    public void cleanDatabase() {
        appointmentRepository.deleteAll();
        doctorRepository.deleteAll();
    }

    @Test
    public void shouldGetAppointmentsSuccess() throws Exception {
        // when
        ResultActions result = mockMvc.perform(
                get("/doctors/{doctorId}/appointments", doctor2Id));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].doctorId", is((int) doctor2Id)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().minusDays(1).toString())))
                .andExpect(jsonPath("$[0].time", is(LocalTime.of(15, 0).toString())))
                .andExpect(jsonPath("$[1].doctorId", is((int) doctor2Id)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$[1].time", is(LocalTime.of(15, 0).toString())));
    }

    @Test
    public void shouldGetAppointmentsFailIfDoctorNotFound404NotFound() throws Exception {
        // when
        ResultActions result = mockMvc.perform(
                get("/doctors/{doctorId}/appointments", 0));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAppointmentsAvailableSuccess() throws Exception {
        // when
        ResultActions result = mockMvc.perform(
                get("/doctors/{doctorId}/appointments?available=true", doctor2Id));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].doctorId", is((int) doctor2Id)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$[0].time", is(LocalTime.of(15, 0).toString())));
    }

    @Test
    public void shouldGetAppointmentsAvailableFailIfDoctorNotFound404NotFound() throws Exception {
        // when
        ResultActions result = mockMvc.perform(
                get("/doctors/{doctorId}/appointments?available=true", 0));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAppointmentsAvailableGivenDateSuccess() throws Exception {
        // given
        String date = LocalDate.now().plusDays(5).toString();

        // when
        ResultActions result = mockMvc.perform(
                get("/doctors/{doctorId}/appointments?available=true&date=" + date, doctor2Id));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].doctorId", is((int) doctor2Id)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().plusDays(5).toString())))
                .andExpect(jsonPath("$[0].time", is(LocalTime.of(15, 0).toString())));
    }

    @Test
    public void shouldGetAppointmentsAvailableGivenDateFailIfDoctorNotFound404NotFound() throws Exception {
        // given
        String date = LocalDate.now().plusDays(5).toString();

        // when
        ResultActions result = mockMvc.perform(
                get("/doctors/{doctorId}/appointments?available=true&date=" + date, 0));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    public void shouldAddAppointmentSuccess200Ok() throws Exception {
        // given
        AppointmentCreationResource resource = createAppointmentCreationResource(
                LocalDate.now().plusDays(1), LocalTime.of(12, 0));

        // when
        ResultActions result = mockMvc.perform(
                post("/doctors/{doctorId}/appointments", doctor1Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.doctorId", is((int) doctor1Id)))
                .andExpect(jsonPath("$.date", is(LocalDate.now().plusDays(1).toString())))
                .andExpect(jsonPath("$.time", is(LocalTime.of(12, 0).toString())));
    }

    @Test
    public void shouldAddAppointmentFailIfResourceValidationFails400BadRequest() throws Exception {
        // given
        AppointmentCreationResource resource = createAppointmentCreationResource(
                LocalDate.now().minusDays(1), LocalTime.of(12, 0));

        // when
        ResultActions result = mockMvc.perform(
                post("/doctors/{doctorId}/appointments", doctor1Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.validationErrors",
                        is(Arrays.asList("Incorrect appointment date"))));
    }

    @Test
    public void shouldAddAppointmentFailIfDoctorNotFound404NotFound() throws Exception {
        // given
        AppointmentCreationResource resource = createAppointmentCreationResource(
                LocalDate.now().minusDays(1), LocalTime.of(12, 0));

        // when
        ResultActions result = mockMvc.perform(
                post("/doctors/{doctorId}/appointments", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)));

        // then
        result.andExpect(status().isNotFound());
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule()).writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AppointmentCreationResource createAppointmentCreationResource(
            LocalDate date, LocalTime time) {
        AppointmentCreationResource resource = new AppointmentCreationResource();

        resource.setDate(date);
        resource.setTime(time);

        return resource;
    }
}