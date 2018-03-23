package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.IncorrectObjectStateException;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.domain.patient.Patient;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppointmentBookedTest {

    @Test
    public void shouldCreateAppointment() {
        // given
        Doctor doctor = new Doctor("First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build();

        // when
        AppointmentBooked appointment = new AppointmentBooked(doctor, date, time, patient, "Sick");

        // then
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertEquals(patient, appointment.getPatient());
        assertEquals("Sick", appointment.getReason());
        assertEquals(AppointmentBookedStatus.NOT_CONFIRMED, appointment.getStatus());
    }

    @Test
    public void shouldChangeStatus() {
        // given
        AppointmentBooked appointment = createAppointment();

        // when
        appointment.changeStatus(AppointmentBookedStatus.CONFIRMED);

        // then
        assertEquals(AppointmentBookedStatus.CONFIRMED, appointment.getStatus());
    }

    @Test
    public void changeStatusShouldThrowExceptionIfItIsTheSame() {
        // given
        AppointmentBooked appointment = createAppointment();

        // expect
        assertThrows(IncorrectObjectStateException.class,
                () -> appointment.changeStatus(AppointmentBookedStatus.NOT_CONFIRMED));
    }

    private AppointmentBooked createAppointment() {
        Doctor doctor = new Doctor("First", "Last", "doctor@domain.com", Specialty.PEDIATRICIAN);
        LocalDate date = LocalDate.of(2017, 9, 12);
        LocalTime time = LocalTime.of(18, 00);
        Patient patient = new Patient.PatientBuilder()
                .firstName("First")
                .lastName("Last")
                .nhsNumber("1234567890")
                .phoneNumber("07522222222")
                .email("fake@gmail.com")
                .build();

        return new AppointmentBooked(doctor, date, time, patient, "Sick");
    }
}