package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.OnlineclinicApplication;
import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedRepository;
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

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Before
    public void setupDatabase() {
        Doctor doctor = new Doctor("First", "Last", Specialty.PEDIATRICIAN);
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

        appointment2Id =  saveAppointment(
                doctor, LocalDate.now().plusDays(5), LocalTime.of(12, 0)).getId();

        Appointment appointment = appointmentRepository.findOne(appointment2Id);
        AppointmentBooked appointmentBooked = new AppointmentBooked(
                doctor, appointment.getDate(), appointment.getTime(),
                patient, "Sick");

        appointment.book();
        doctor.addAppointmentBooked(appointmentBooked);
        patient.addAppointmentBooked(appointmentBooked);

        appointmentBookedRepository.save(appointmentBooked);
        doctorRepository.save(doctor);
        patientRepository.save(patient);
        appointmentRepository.save(appointment);
    }

    @After
    public void cleanDatabase() {
        appointmentRepository.deleteAll();
        appointmentBookedRepository.deleteAll();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
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
                .andExpect(jsonPath("$.[0]reason", is("Sick")));
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

    private Appointment saveAppointment(Doctor doctor, LocalDate date, LocalTime time) {
        Appointment appointment = new Appointment(
                doctor, date, time);

        doctor.addAppointment(appointment);
        Appointment appointmentSaved = appointmentRepository.save(appointment);
        doctorRepository.save(doctor);

        return appointmentSaved;
    }
}