package com.joanna.onlineclinic.web.patient;

import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientRepositoryStub;
import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatientCreationValidatorTest {

    private PatientService service = new PatientService(new PatientRepositoryStub());
    private PatientCreationValidator validator = new PatientCreationValidator(service);

    @Test
    public void shouldValidateWithNoErrors() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "7522222222", "some@domain.com");

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

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfFirstNameIsBlank() {
        // given
        PatientResource resource = createPatientResource("  ", "Last",
                "1234567890", "7522222222", "some@domain.com");

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

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfLastNameIsBlank() {
        // given
        PatientResource resource = createPatientResource("First", "  ",
                "1234567890", "7522222222", "some@domain.com");

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

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect NHS number"));
    }

    @Test
    public void shouldValidateWithErrorIfNhsNumberExists() {
        // given
        PatientResource firstPatientResource = createPatientResource("A", "D",
                "1234567890", "7522222222", "some1@domain.com");
        service.registerPatient(firstPatientResource);

        PatientResource secondPatientResource = createPatientResource("First", "Last",
            "1234567890", "7522222222", "some2@domain.com");


        // when
        ErrorsResource errorsResource = validator.validate(secondPatientResource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Patient with given NHS number exists"));
    }

    @Test
    public void shouldValidateWithNoErrorIfPhoneNumberIsEmpty() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "", "some@domain.com");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithNoErrorIfPhoneNumberIsBlank() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "   ", "some@domain.com");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldValidateWithErrorIfPhoneNumberIsIncorrect() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "765522441d", "some@domain.com");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect phone number"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsEmpty() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "7722222222", "");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Email address not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsBlank() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "7722222222", "  ");

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

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect email address"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsNotUnique() {
        // given
        PatientResource resource = createPatientResource("First", "Last",
                "1234567890", "7722222222", "something@domain.com");
        PatientResource resource2 = createPatientResource("FirstP", "LastP",
                "", "7722222222", "something@domain.com");

        service.registerPatient(resource2);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Email already registered"));
    }

    @Test
    public void shouldValidateWithErrors() {
        // given
        PatientResource firstPatientResource = createPatientResource("A", "D",
            "1234567890", "7522222222", "some@domain.com");
        service.registerPatient(firstPatientResource);

        PatientResource resource = createPatientResource(null, null,
                "1234567890", null, "something@domain");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(4, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("Patient with given NHS number exists"));
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect email address"));
    }

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
}