package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.DoctorRepositoryStub;
import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoctorCreationValidatorTest {

    private DoctorService service = new DoctorService(new DoctorRepositoryStub());
    private DoctorCreationValidator validator = new DoctorCreationValidator(service);

    private DoctorResource createDoctorResource(
            String firstName, String lastName, String email, Specialty specialty) {
        DoctorResource resource = new DoctorResource();

        resource.setFirstName(firstName);
        resource.setLastName(lastName);
        resource.setEmail(email);
        resource.setSpecialty(specialty);

        return resource;
    }

    @Test
    public void shouldNotHaveErrors() {
        // given
        DoctorResource resource = createDoctorResource(
                "Frist", "Last", "doctor@domain.com", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldHaveErrorIfFirstNameIsNull() {
        // given
        DoctorResource resource = createDoctorResource(
                null, "Last", "doctor@domain.com", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }

    @Test
    public void shouldHaveErrorIfLastNameIsNull() {
        // given
        DoctorResource resource = createDoctorResource(
                "First", null, "doctor@domain.com", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
    }

    @Test
    public void shouldHaveErrorIfFirstNameConsistsOfSpaces() {
        // given
        DoctorResource resource = createDoctorResource(
                "   ", "Last", "doctor@domain.com", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }

    @Test
    public void shouldHaveErrorIfLastNameConsistsOfSpaces() {
        // given
        DoctorResource resource = createDoctorResource(
                "First", "   ", "doctor@domain.com", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsEmpty() {
        // given
        DoctorResource resource = createDoctorResource(
                "First", "Last", "", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Email address not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsBlank() {
        // given
        DoctorResource resource = createDoctorResource(
                "First", "Last", "  ", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Email address not specified"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsIncorrect() {
        // given
        DoctorResource resource = createDoctorResource(
                "First", "Last", "something@domain", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Incorrect email address"));
    }

    @Test
    public void shouldValidateWithErrorIfEmailIsNotUnique() {
        // given
        DoctorResource resource = createDoctorResource(
                "First", "Last", "something@domain.com", Specialty.GENERAL_PHYSICIAN);
        DoctorResource resource2 = createDoctorResource(
                "FirstP", "LastP", "something@domain.com", Specialty.GENERAL_PHYSICIAN);

        service.registerDoctor(resource2);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Email already registered"));
    }

    @Test
    public void shouldHaveErrorIfSpecialtyIsNull() {
        // given
        DoctorResource resource = createDoctorResource(
                "First", "Last", "doctor@domain.com", null);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Specialty not specified"));
    }

    @Test
    public void shouldHaveMultipleErrors() {
        // given
        DoctorResource resource = createDoctorResource(
                "", "", "doctor@domain.com", null);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(3, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Specialty not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }
}