package com.joanna.onlineclinic.web.appointment.available;

import com.joanna.onlineclinic.domain.appointment.available.AppointmentAvailableService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppointmentAvailableCreationValidatorTest {

    private AppointmentAvailableService service = mock(AppointmentAvailableService.class);
    private AppointmentAvailableCreationValidator validator = new AppointmentAvailableCreationValidator(service);

    private AppointmentAvailableResource createAppointmentResource(LocalDateTime dateTime) {
        AppointmentAvailableResource resource = new AppointmentAvailableResource();

        resource.setAppointmentDateTime(dateTime);

        return resource;
    }

    @Test
    public void shouldValidateWithNoErrors() {
        // given
        AppointmentAvailableResource resource = createAppointmentResource(LocalDateTime.of(2018, 3, 12, 15, 30));

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithErrorIfDateIsIncorrect() {
        // given
        AppointmentAvailableResource resource = createAppointmentResource(LocalDateTime.now().minusDays(10));

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect appointment date"));
    }

    @Test
    public void shouldValidateWithErrorIfDateIsNotSpecified() {
        // given
        AppointmentAvailableResource resource = createAppointmentResource(null);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment date not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfAppointmentExists() {
        // given
        AppointmentAvailableResource resource = createAppointmentResource(LocalDateTime.now().minusDays(10));
        when(service.appointmentExists(resource)).thenReturn(true);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect appointment date"));
    }
}