package com.joanna.onlineclinic.web.patient;

import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<Object> addPatient(@RequestBody PatientResource resource) {
        LOGGER.info("Patient added: first name: {}, last name: {}, NHS: {}, phone: {}, email: {}",
                resource.getFirstName(), resource.getLastName(), resource.getNhsNumber(),
                resource.getPhoneNumber(), resource.getEmail());

        ErrorsResource errorsResource = validator.validate(resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            Patient patient = service.registerPatient(resource);

            return new ResponseEntity<Object>(getPatientResource(patient), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(errorsResource, HttpStatus.BAD_REQUEST);
        }
    }

    private PatientResource getPatientResource(Patient patient) {
        return new PatientResource(patient);
    }
}
