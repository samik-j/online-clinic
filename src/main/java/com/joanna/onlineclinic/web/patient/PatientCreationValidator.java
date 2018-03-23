package com.joanna.onlineclinic.web.patient;

import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientCreationValidator {

    private PatientService service;

    public PatientCreationValidator(PatientService service) {
        this.service = service;
    }

    ErrorsResource validate(PatientResource resource) {
        List<String> validationErrors = new ArrayList<>();

        if (StringUtils.isBlank(resource.getFirstName())) {
            validationErrors.add("First name not specified");
        }
        if (StringUtils.isBlank(resource.getLastName())) {
            validationErrors.add("Last name not specified");
        }
        if (StringUtils.isNotBlank(resource.getNhsNumber())) {
            if (resource.getNhsNumber().replaceAll("\\s", "").length() != 10) {
                validationErrors.add("Incorrect NHS number");
            } else if (!isNhsNumberUnique(resource.getNhsNumber())) {
                validationErrors.add("Patient with given NHS number exists");
            }
        }
        if (!StringUtils.isBlank(resource.getPhoneNumber())
                && !StringUtils.isNumeric(resource.getPhoneNumber())) {
            validationErrors.add("Incorrect phone number");
        }
        if (StringUtils.isBlank(resource.getEmail())) {
            validationErrors.add("Email address not specified");
        } else if (!isEmailValid(resource.getEmail())) {
            validationErrors.add("Incorrect email address");
        } else if (!isEmailUnique(resource.getEmail())) {
            validationErrors.add("Email already registered");
        }

        return new ErrorsResource(validationErrors);
    }

    private boolean isEmailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isEmailUnique(String email) {
        return !service.existsByEmail(email);
    }

    private boolean isNhsNumberUnique(String nhsNumber) {
        return !service.existsByNhsNumber(nhsNumber);
    }
}
