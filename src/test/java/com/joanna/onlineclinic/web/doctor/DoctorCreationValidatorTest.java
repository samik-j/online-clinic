package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DoctorCreationValidatorTest {

    private DoctorCreationValidator validator = new DoctorCreationValidator();

    private DoctorResource createDoctorResource(String firstName, String lastName, Specialty specialty) {
        DoctorResource resource = new DoctorResource();

        resource.setFirstName(firstName);
        resource.setLastName(lastName);
        resource.setSpecialty(specialty);

        return resource;
    }

    @Test
    public void shouldNotHaveErrors() {
        // given
        DoctorResource resource = createDoctorResource("Frist", "Last", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldHaveErrorIfFirstNameIsNull() {
        // given
        DoctorResource resource = createDoctorResource(null, "Last", Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }

    @Test
    public void shouldHaveErrorIfLastNameIsNull() {
        // given
        DoctorResource resource = createDoctorResource("First", null, Specialty.GENERAL_PHYSICIAN);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
    }

    @Test
    public void shouldHaveErrorIfSpecialtyIsNull() {
        // given
        DoctorResource resource = createDoctorResource("First", "Last", null);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Specialty not specified"));
    }

    @Test
    public void shouldHaveMultipleErrors() {
        // given
        DoctorResource resource = createDoctorResource("", "", null);

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(3, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Specialty not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }
}