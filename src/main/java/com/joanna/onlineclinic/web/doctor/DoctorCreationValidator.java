package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorCreationValidator {

    private DoctorService service;

    public DoctorCreationValidator(DoctorService service) {
        this.service = service;
    }

    ErrorsResource validate(DoctorResource resource) {
        List<String> validationErrors = new ArrayList<>();

        if (StringUtils.isBlank(resource.getFirstName())) {
            validationErrors.add("First name not specified");
        }
        if (StringUtils.isBlank(resource.getLastName())) {
            validationErrors.add("Last name not specified");
        }
        if (StringUtils.isBlank(resource.getEmail())) {
            validationErrors.add("Email address not specified");
        } else if (!isEmailValid(resource.getEmail())) {
            validationErrors.add("Incorrect email address");
        } else if (!isEmailUnique(resource.getEmail())) {
            validationErrors.add("Email already registered");
        }
        if (resource.getSpecialty() == null) {
            validationErrors.add("Specialty not specified");
        }

        return new ErrorsResource(validationErrors);
    }

    private boolean isEmailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isEmailUnique(String email) {
        return !service.existsByEmail(email);
    }
}
