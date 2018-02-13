package com.joanna.onlineclinic.web.doctor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joanna.onlineclinic.OnlineclinicApplication;
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
public class DoctorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    private long doctor1Id;

    @Before
    public void createDoctors() {
        Doctor doctor1 = new Doctor("First1", "Last1", Specialty.PEDIATRICIAN);
        Doctor doctor2 = new Doctor("First2", "Last2", Specialty.GYNAECOLOGIST);

        doctor1Id = doctorRepository.save(doctor1).getId();
        doctorRepository.save(doctor2);
    }

    @After
    public void cleanDatabase() {
        doctorRepository.deleteAll();
    }

    @Test
    public void shouldGetDoctorsSuccess() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/doctors"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("First1")))
                .andExpect(jsonPath("$[0].lastName", is("Last1")))
                .andExpect(jsonPath("$[0].specialty", is("PEDIATRICIAN")))
                .andExpect(jsonPath("$[1].firstName", is("First2")))
                .andExpect(jsonPath("$[1].lastName", is("Last2")))
                .andExpect(jsonPath("$[1].specialty", is("GYNAECOLOGIST")));
    }

    @Test
    public void shouldGetDoctorsBySpecialtySuccess() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/doctors?specialty=PEDIATRICIAN"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("First1")))
                .andExpect(jsonPath("$[0].lastName", is("Last1")))
                .andExpect(jsonPath("$[0].specialty", is("PEDIATRICIAN")));
    }

    @Test
    public void shouldGetDoctorSuccess() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/doctors/{doctorId}", doctor1Id));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.firstName", is("First1")))
                .andExpect(jsonPath("$.lastName", is("Last1")))
                .andExpect(jsonPath("$.specialty", is("PEDIATRICIAN")));
    }

    @Test
    public void shouldAddDoctorSuccess() throws Exception {
        // given
        DoctorResource resource = createDoctorResource(
                "First", "Last", Specialty.DERMATOLOGIST);

        // when
        ResultActions result = mockMvc.perform(post("/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resource)));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.firstName", is("First")))
                .andExpect(jsonPath("$.lastName", is("Last")))
                .andExpect(jsonPath("$.specialty", is("DERMATOLOGIST")));
    }

    @Test
    public void shouldAddDoctorFailIfResourceValidationFails400BadRequest() throws Exception {
        // given
        DoctorResource resource = createDoctorResource(
                "", "Last", Specialty.DERMATOLOGIST);

        // when
        ResultActions result = mockMvc.perform(post("/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resource)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.validationErrors",
                        is(Arrays.asList("First name not specified"))));
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DoctorResource createDoctorResource(
            String firstName, String lastName, Specialty specialty) {
        DoctorResource resource = new DoctorResource();

        resource.setFirstName(firstName);
        resource.setLastName(lastName);
        resource.setSpecialty(specialty);

        return resource;
    }

}