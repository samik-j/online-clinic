package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DoctorCreationValidator {

    private DoctorService service;

    public DoctorCreationValidator(DoctorService service) {
        this.service = service;
    }

    ErrorsResource validate(DoctorResource resource) {
        List<String> validationErrors = new ArrayList<>();

        if (resource.getFirstName() == null || resource.getFirstName().isEmpty()) {
            validationErrors.add("First name not specified");
        }
        if (resource.getLastName() == null || resource.getLastName().isEmpty()) {
            validationErrors.add("Last name not specified");
        }
        if (resource.getSpecialty() == null) {
            validationErrors.add("Specialty not specified");
        }

        return new ErrorsResource(validationErrors);
    }

}
