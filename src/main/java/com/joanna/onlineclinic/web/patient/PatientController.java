package com.joanna.onlineclinic.web.patient;

import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        LOGGER.info("Adding patient: first name: {}, last name: {}, NHS: {}, phone: {}, email: {}",
                resource.getFirstName(), resource.getLastName(), resource.getNhsNumber(),
                resource.getPhoneNumber(), resource.getEmail());

        ErrorsResource errorsResource = validator.validate(resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            Patient patient = service.registerPatient(resource);

            return new ResponseEntity<Object>(getPatientResource(patient), HttpStatus.OK);
        } else {
            LOGGER.error("Failed: " + errorsResource.getValidationErrors().toString());
            return new ResponseEntity<Object>(errorsResource, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<PatientResource> getPatients() {
        return getPatientResourceList(service.findAll());
    }

    private PatientResource getPatientResource(Patient patient) {
        return new PatientResource(patient);
    }

    private List<PatientResource> getPatientResourceList(List<Patient> patients) {
        return patients.stream().map(PatientResource::new).collect(Collectors.toList());
    }
}
