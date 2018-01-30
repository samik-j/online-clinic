package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.AppointmentService;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedService;
import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class AppointmentBookedCreationResourceValidatorTest {

    @Mock
    private AppointmentService appointmentService;
    @Mock
    private AppointmentBookedService appointmentBookedService;
    @Mock
    private PatientService patientService;

    private AppointmentBookedCreationResourceValidator validator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        validator = new AppointmentBookedCreationResourceValidator(
                appointmentService, appointmentBookedService, patientService);
    }

    private AppointmentBookedCreationResource createResource(
            Long appointmentId, Long patientId, String reason) {
        AppointmentBookedCreationResource resource = new AppointmentBookedCreationResource();

        resource.setAppointmentId(appointmentId);
        resource.setPatientId(patientId);
        resource.setReason(reason);

        return resource;
    }

    @Test
    public void shouldValidateWithNoErrors() {
        // given
        Long appointmentId = 1L;
        Long patientId = 3L;

        AppointmentBookedCreationResource resource = createResource(
                appointmentId, patientId, "I am sick");

        when(appointmentService.appointmentExists(appointmentId)).thenReturn(true);
        when(appointmentService.isAvailable(appointmentId)).thenReturn(true);
        when(patientService.patientExists(patientId)).thenReturn(true);
        when(appointmentBookedService.appointmentExists(resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithErrorIfAppointmentDoesNotExist() {
        // given
        Long appointmentId = 1L;
        Long patientId = 3L;

        AppointmentBookedCreationResource resource = createResource(
                appointmentId, patientId, "I am sick");

        when(appointmentService.appointmentExists(appointmentId)).thenReturn(false);
        when(patientService.patientExists(patientId)).thenReturn(true);
        when(appointmentBookedService.appointmentExists(resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment not available"));
    }

    @Test
    public void shouldValidateWithErrorIfAppointmentIsNotAvailable() {
        // given
        Long appointmentId = 1L;
        Long patientId = 3L;

        AppointmentBookedCreationResource resource = createResource(
                appointmentId, patientId, "I am sick");

        when(appointmentService.appointmentExists(appointmentId)).thenReturn(true);
        when(appointmentService.isAvailable(appointmentId)).thenReturn(false);
        when(patientService.patientExists(patientId)).thenReturn(true);
        when(appointmentBookedService.appointmentExists(resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment not available"));
    }

    @Test
    public void shouldValidateWithErrorIfPatientIdIsNull() {
        // given
        Long appointmentId = 1L;
        Long patientId = null;

        AppointmentBookedCreationResource resource = createResource(
                appointmentId, patientId, "I am sick");

        when(appointmentService.appointmentExists(appointmentId)).thenReturn(true);
        when(appointmentService.isAvailable(appointmentId)).thenReturn(true);
        when(appointmentBookedService.appointmentExists(resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Patient id not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfPatientDoesNotExist() {
        // given
        Long appointmentId = 1L;
        Long patientId = 3L;

        AppointmentBookedCreationResource resource = createResource(
                appointmentId, patientId, "I am sick");

        when(appointmentService.appointmentExists(appointmentId)).thenReturn(true);
        when(appointmentService.isAvailable(appointmentId)).thenReturn(true);
        when(patientService.patientExists(patientId)).thenReturn(false);
        when(appointmentBookedService.appointmentExists(resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect patient id"));
    }

    @Test
    public void shouldValidateWithErrorIfAppointmentBookedExists() {
        // given
        Long appointmentId = 1L;
        Long patientId = 3L;

        AppointmentBookedCreationResource resource = createResource(
                appointmentId, patientId, "I am sick");

        when(appointmentService.appointmentExists(appointmentId)).thenReturn(true);
        when(appointmentService.isAvailable(appointmentId)).thenReturn(true);
        when(patientService.patientExists(patientId)).thenReturn(true);
        when(appointmentBookedService.appointmentExists(resource)).thenReturn(true);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment already booked"));
    }

    @Test
    public void shouldValidateWithErrorIfReasonHasTooManyCharacters() {
        // given
        Long appointmentId = 1L;
        Long patientId = 3L;
        String reason = "I am sick I am sick I am sick I am sick I am sick I am sick I am sick" +
                " I am sick I am sick I am sick I am sick I am sick I am sick I am sick I am s" +
                "ick I am sick I am sick I am sick I am sick I am sick I am sick I am sick I a" +
                "m sick I am sick I am sick I am sick I";

        AppointmentBookedCreationResource resource = createResource(
                appointmentId, patientId, reason);

        when(appointmentService.appointmentExists(appointmentId)).thenReturn(true);
        when(appointmentService.isAvailable(appointmentId)).thenReturn(true);
        when(patientService.patientExists(patientId)).thenReturn(true);
        when(appointmentBookedService.appointmentExists(resource)).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors()
                .contains("Exceeded maximum number of characters for reason"));
    }

    @Test
    public void shouldValidateWithErrors() {
        // given
        Long appointmentId = 1L;
        Long patientId = 3L;
        String reason = "I am sick I am sick I am sick I am sick I am sick I am sick I am sick" +
                " I am sick I am sick I am sick I am sick I am sick I am sick I am sick I am s" +
                "ick I am sick I am sick I am sick I am sick I am sick I am sick I am sick I a" +
                "m sick I am sick I am sick I am sick I";

        AppointmentBookedCreationResource resource = createResource(
                appointmentId, patientId, reason);

        when(appointmentService.appointmentExists(appointmentId)).thenReturn(true);
        when(appointmentService.isAvailable(appointmentId)).thenReturn(false);
        when(patientService.patientExists(patientId)).thenReturn(false);
        when(appointmentBookedService.appointmentExists(resource)).thenReturn(true);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(4, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Appointment not available"));
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect patient id"));
        assertTrue(errorsResource.getValidationErrors().contains("Appointment already booked"));
        assertTrue(errorsResource.getValidationErrors()
                .contains("Exceeded maximum number of characters for reason"));
    }
}