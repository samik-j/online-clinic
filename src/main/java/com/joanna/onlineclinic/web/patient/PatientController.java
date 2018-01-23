package com.joanna.onlineclinic.web.patient;

import com.joanna.onlineclinic.domain.patient.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);
    private PatientService service;
    private PatientCreationValidator validator;

    public PatientController(PatientService service, PatientCreationValidator validator) {
        this.service = service;
        this.validator = validator;
    }
}
