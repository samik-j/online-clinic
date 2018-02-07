package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.web.ErrorsResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorCreationValidator {

    ErrorsResource validate(DoctorResource resource) {
        List<String> validationErrors = new ArrayList<>();

        if (StringUtils.isBlank(resource.getFirstName())) {
            validationErrors.add("First name not specified");
        }
        if (StringUtils.isBlank(resource.getLastName())) {
            validationErrors.add("Last name not specified");
        }
        if (resource.getSpecialty() == null) {
            validationErrors.add("Specialty not specified");
        }

        return new ErrorsResource(validationErrors);
    }

}
