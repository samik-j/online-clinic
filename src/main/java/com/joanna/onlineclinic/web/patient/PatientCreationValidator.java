package com.joanna.onlineclinic.web.patient;

import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
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

        if (resource.getFirstName() == null || resource.getFirstName().isEmpty()) {
            validationErrors.add("First name not specified");
        }
        if (resource.getLastName() == null || resource.getLastName().isEmpty()) {
            validationErrors.add("Last name not specified");
        }
        if (resource.getNhsNumber() != null) {
            if (resource.getNhsNumber().replaceAll("\\s", "").length() != 10) {
                validationErrors.add("Incorrect NHS number");
            } else if (!isNhsNumberUnique(resource.getNhsNumber())) {
                validationErrors.add("Patient with given NHS number exists");
            }
        }
        if (resource.getPhoneNumber() == null || resource.getPhoneNumber().isEmpty()) {
            validationErrors.add("Phone number not specified");
        }
        if (resource.getEmail() == null || resource.getEmail().isEmpty()) {
            validationErrors.add("Email address not specified");
        } else if (!isEmailValid(resource.getEmail())) {
            validationErrors.add("Incorrect email address format");
        }

        return new ErrorsResource(validationErrors);
    }

    private boolean isEmailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isNhsNumberUnique(String nhsNumber) {
        return !service.nhsNumberExists(nhsNumber);
    }
}
