package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DoctorCreationValidatorTest {

    private DoctorService service = mock(DoctorService.class);
    private DoctorCreationValidator validator = new DoctorCreationValidator(service);

    private DoctorResource createDoctorResource(String firstName, String lastName, String specialty) {
        DoctorResource resource = new DoctorResource();

        resource.setFirstName(firstName);
        resource.setLastName(lastName);
        resource.setSpecialty(specialty);

        return resource;
    }

    @Test
    public void shouldNotHaveErrors() {
        // given
        DoctorResource resource = createDoctorResource("Frist", "Last", "gynaecologist");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertTrue(errorsResource.getValidationErrors().isEmpty());
    }

    @Test
    public void shouldHaveErrorIfFirstNameIsNull() {
        // given
        DoctorResource resource = createDoctorResource(null, "Last", "gynaecologist");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }

    @Test
    public void shouldHaveErrorIfLastNameIsNull() {
        // given
        DoctorResource resource = createDoctorResource("First", null, "gynaecologist");

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
    public void shouldHaveErrorIfSpecialtyIsInvalid() {
        // given
        DoctorResource resource = createDoctorResource("First", "Last", "something");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(1, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Specialty not valid"));
    }

    @Test
    public void shouldHaveMultipleErrors() {
        // given
        DoctorResource resource = createDoctorResource("", "", "");

        // when
        ErrorsResource errorsResource = validator.validate(resource);

        // then
        assertEquals(3, errorsResource.getValidationErrors().size());
        assertTrue(errorsResource.getValidationErrors().contains("Specialty not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("Last name not specified"));
        assertTrue(errorsResource.getValidationErrors().contains("First name not specified"));
    }
}