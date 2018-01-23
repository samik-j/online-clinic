package com.joanna.onlineclinic.web.patient;

import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatientCreationValidatorTest {

    private PatientService service = mock(PatientService.class);
    private PatientCreationValidator validator = new PatientCreationValidator(service);

    private PatientResource createPatientResource(String firstName, String lastName,
                                                  String nhsNumber, String phoneNumber,
                                                  String email) {
        PatientResource resource = new PatientResource();

        resource.setFirstName(firstName);
        resource.setLastName(lastName);
        resource.setNhsNumber(nhsNumber);
        resource.setPhoneNumber(phoneNumber);
        resource.setEmail(email);

        return resource;
    }

    @Test
    public void shouldValidateWithNoErrors() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "7522222222", "some@domain.com");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithNoErrorsIfNhsNumberIsEmpty() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "", "7522222222", "some@domain.com");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithNoErrorsIfNhsNumberIsNull() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                null, "7522222222", "some@domain.com");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithErrorIfFirstNameIsEmpty() {
        // given
        PatientResource resource = createPatientResource("", "Last",
                "1234567890", "7522222222", "some@domain.com");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfLastNameIsEmpty() {
        // given
        PatientResource resource = createPatientResource("First", "",
                "1234567890", "7522222222", "some@domain.com");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfNhsNumberIsIncorrect() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "12345", "7522222222", "some@domain.com");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect NHS number"));
    }

    @Test
    public void shouldValidateWithErrorIfNhsNumberExists() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "7522222222", "some@domain.com");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(true);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Patient with given NHS number exists"));
    }

    @Test
    public void shouldValidateWithErrorIfPhoneNumberIsEmpty() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "", "some@domain.com");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Phone number not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsEmpty() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "7722222222", "");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Email address not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsIncorrect() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "7722222222", "something@domain");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(false);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect email address"));
    }

    @Test
    public void shouldValidateWithErrors() {
        // given
        PatientResource resource = createPatientResource(null, null,
                "1234567890", null, "something@domain");
        when(service.nhsNumberExists(resource.getNhsNumber())).thenReturn(true);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(5, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("Patient with given NHS number exists"));
        assertTrue(errorsResource.getValidationErrors().contains("Phone number not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect email address"));
    }
}