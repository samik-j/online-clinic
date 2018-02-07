package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.domain.doctor.Specialty;
import com.joanna.onlineclinic.web.ErrorsResource;
import com.joanna.onlineclinic.web.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        LOGGER.info("Adding doctor: first name: {}, last name: {}, specialty: {}",
                resource.getFirstName(), resource.getLastName(), resource.getSpecialty());

        ErrorsResource errorsResource = creationValidator.validate(resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            Doctor doctor = doctorService.registerDoctor(resource);

            return new ResponseEntity<Object>(getDoctorResource(doctor), HttpStatus.OK);
        } else {
            LOGGER.info("Failed: {}", errorsResource.getValidationErrors().toString());
            return new ResponseEntity<Object>(errorsResource, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<DoctorResource> getDoctors() {
        return getDoctorResourceList(doctorService.findDoctors());
    }

    @GetMapping(params = {"specialty"})
    public List<DoctorResource> getDoctors(@RequestParam Specialty specialty) {
        return getDoctorResourceList(doctorService.findDoctors(specialty));
    }

    @GetMapping("/{doctorId}")
    public DoctorResource getDoctor(@PathVariable long doctorId) {
        Doctor doctor = doctorService.findDoctorById(doctorId);

        if (doctor != null) {
            return getDoctorResource(doctor);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    private DoctorResource getDoctorResource(Doctor doctor) {
        return new DoctorResource(doctor);
    }

    private List<DoctorResource> getDoctorResourceList(List<Doctor> doctors) {
        List<DoctorResource> doctorResources = new ArrayList<>();

        for (Doctor doctor : doctors) {
            doctorResources.add(getDoctorResource(doctor));
        }

        return doctorResources;
    }
}
