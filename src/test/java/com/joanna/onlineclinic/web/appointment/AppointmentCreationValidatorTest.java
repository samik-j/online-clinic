package com.joanna.onlineclinic.web.appointment;

import com.joanna.onlineclinic.domain.appointment.AppointmentService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppointmentCreationValidatorTest {

    private AppointmentService service = mock(AppointmentService.class);
    private AppointmentCreationValidator validator = new AppointmentCreationValidator(service);

    private long doctorId = 1;

    private AppointmentCreationResource createAppointmentCreationResource(LocalDate date, LocalTime time) {
        AppointmentCreationResource resource = new AppointmentCreationResource();

        resource.setDate(date);
        resource.setTime(time);

        return resource;
    }

    @Test
    public void shouldValidateWithNoErrors() {
        // given
        AppointmentCreationResource resource = createAppointmentCreationResource(
                LocalDate.now().plusDays(5), LocalTime.of(15, 30));

        when(service.appointmentExists(doctorId, resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithErrorIfDateIsIncorrect() {
        // given
        AppointmentCreationResource resource =
                createAppointmentCreationResource(LocalDate.now().minusDays(10), LocalTime.of(15, 00));

        when(service.appointmentExists(doctorId, resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect appointment date"));
    }

    @Test
    public void shouldValidateWithErrorIfDateIsNotSpecified() {
        // given
        AppointmentCreationResource resource =
                createAppointmentCreationResource(null, LocalTime.of(15, 00));

        when(service.appointmentExists(doctorId, resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment date not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfTimeIsIncorrect() {
        // given
        AppointmentCreationResource resource =
                createAppointmentCreationResource(LocalDate.now(), LocalTime.now().minusHours(2));

        when(service.appointmentExists(doctorId, resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect appointment time"));
    }

    @Test
    public void shouldValidateWithErrorIfTimeIsNotSpecified() {
        // given
        AppointmentCreationResource resource =
                createAppointmentCreationResource(LocalDate.now().plusDays(5), null);

        when(service.appointmentExists(doctorId, resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment time not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfAppointmentExists() {
        // given
        AppointmentCreationResource resource =
                createAppointmentCreationResource(LocalDate.now().plusDays(5), LocalTime.of(15, 00));

        when(service.appointmentExists(doctorId, resource)).thenReturn(true);

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment already exists"));
    }
}