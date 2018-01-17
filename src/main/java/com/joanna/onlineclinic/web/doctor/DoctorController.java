package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorService;
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
@RequestMapping("/doctors")
public class DoctorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class);
    private DoctorService doctorService;
    private DoctorCreationValidator creationValidator;

    public DoctorController(DoctorService doctorService, DoctorCreationValidator creationValidator) {
        this.doctorService = doctorService;
        this.creationValidator = creationValidator;
    }

    @PostMapping
    public ResponseEntity<Object> addDoctor(@RequestBody DoctorResource resource) {
        LOGGER.info("Doctor added : first name: {}, last name: {}, specialty: {}",
                resource.getFirstName(), resource.getLastName(), resource.getSpecialty());

        ErrorsResource errorsResource = creationValidator.validate(resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            Doctor doctor = doctorService.registerDoctor(resource);

            return new ResponseEntity<Object>(getDoctorResource(doctor), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(errorsResource, HttpStatus.BAD_REQUEST);
        }
    }

    private DoctorResource getDoctorResource(Doctor doctor) {
        return new DoctorResource(doctor);
    }
}
