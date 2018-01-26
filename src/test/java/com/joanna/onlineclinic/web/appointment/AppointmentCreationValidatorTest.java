package com.joanna.onlineclinic.web.appointment;

import com.joanna.onlineclinic.domain.appointment.AppointmentService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppointmentCreationValidatorTest {

    private AppointmentService service =
            mock(AppointmentService.class);
    private AppointmentCreationValidator validator =
            new AppointmentCreationValidator(service);

    private long doctorId = 1;

    private AppointmentResource createAppointmentResource(LocalDateTime dateTime) {
        AppointmentResource resource = new AppointmentResource();

        resource.setAppointmentDateTime(dateTime);

        return resource;
    }

    @Test
    public void shouldValidateWithNoErrors() {
        // given
        AppointmentResource resource =
                createAppointmentResource(LocalDateTime.of(2018, 3, 12, 15, 30));

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithErrorIfDateIsIncorrect() {
        // given
        AppointmentResource resource =
                createAppointmentResource(LocalDateTime.now().minusDays(10));

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect appointment date"));
    }

    @Test
    public void shouldValidateWithErrorIfDateIsNotSpecified() {
        // given
        AppointmentResource resource =
                createAppointmentResource(null);

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment date not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfAppointmentExists() {
        // given
        AppointmentResource resource =
                createAppointmentResource(LocalDateTime.now().minusDays(10));

        when(service.appointmentExists(doctorId, resource)).thenReturn(true);

        // when
        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect appointment date"));
    }
}