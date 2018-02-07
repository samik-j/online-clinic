package com.joanna.onlineclinic.web.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joanna.onlineclinic.OnlineclinicApplication;
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

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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
public class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Before
    public void setupDatabase() {
        Patient patient = new Patient("FirstP", "LastP", "1234567890",
                "7522222222", "some@domain.com");

        patientRepository.save(patient);
    }

    @After
    public void cleanDatabase() {
        patientRepository.deleteAll();
    }

    @Test
    public void shouldAddPatientSuccess200Ok() throws Exception {
        // given
        PatientResource resource = createPatientResource("FirstName", "Last",
                null, "7522332233", "someone@domain.com");

        // when
        ResultActions result = mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resource)));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.firstName", is("FirstName")))
                .andExpect(jsonPath("$.lastName", is("Last")))
                .andExpect(jsonPath("$.nhsNumber", nullValue()))
                .andExpect(jsonPath("$.phoneNumber", is("7522332233")))
                .andExpect(jsonPath("$.email", is("someone@domain.com")));
    }

    @Test
    public void shouldAddPatientFailIfResourceValidationFails400BadRequest() throws Exception {
        // given
        PatientResource resource = createPatientResource("", "Last",
                null, "7522332233", "someone@domain.com");

        // when
        ResultActions result = mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resource)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.validationErrors",
                        is(Arrays.asList("First name not specified"))));
    }

    @Test
    public void shouldGetPatientsSuccess() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/patients"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("FirstP")))
                .andExpect(jsonPath("$[0].lastName", is("LastP")))
                .andExpect(jsonPath("$[0].nhsNumber", is("1234567890")))
                .andExpect(jsonPath("$[0].phoneNumber", is("7522222222")))
                .andExpect(jsonPath("$[0].email", is("some@domain.com")));
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PatientResource createPatientResource(
            String firstName, String lastName, String nhsNumber, String phoneNumber, String email) {
        PatientResource resource = new PatientResource();

        resource.setFirstName(firstName);
        resource.setLastName(lastName);
        resource.setNhsNumber(nhsNumber);
        resource.setPhoneNumber(phoneNumber);
        resource.setEmail(email);

        return resource;
    }
}