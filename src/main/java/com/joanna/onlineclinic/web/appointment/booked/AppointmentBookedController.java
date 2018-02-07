package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointmentsBooked")
public class AppointmentBookedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentBookedController.class);
    private AppointmentBookedCreationResourceValidator validator;
    private AppointmentBookedService service;

    public AppointmentBookedController(AppointmentBookedCreationResourceValidator validator,
                                       AppointmentBookedService service) {
        this.validator = validator;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> bookAppointment(
            @RequestBody AppointmentBookedCreationResource resource) {
        LOGGER.info("Booking appointment: appointment id: {}, patient id: {}, reason: {}",
                resource.getAppointmentId(), resource.getPatientId(), resource.getReason());

        ErrorsResource errorsResource = validator.validate(resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            return new ResponseEntity<Object>(
                    getAppointmentBookedResource(service.addAppointment(resource)), HttpStatus.OK);
        } else {
            LOGGER.info("Failed: {}", errorsResource.getValidationErrors().toString());
            return new ResponseEntity<Object>(
                    errorsResource.getValidationErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(params = {"doctorId"})
    public List<AppointmentBookedResource> getAppointmentsByDoctorId(@RequestParam long doctorId) {
        return getAppointmentBookedResources(service.findByDoctorId(doctorId));
    }

    @GetMapping(params = {"patientId"})
    public List<AppointmentBookedResource> getAppointmentsByPatientId(@RequestParam long patientId) {
        return getAppointmentBookedResources(service.findByPatientId(patientId));
    }

    private AppointmentBookedResource getAppointmentBookedResource(AppointmentBooked appointment) {
        return new AppointmentBookedResource(appointment);
    }

    private List<AppointmentBookedResource> getAppointmentBookedResources(
            List<AppointmentBooked> appointments) {
        return appointments.stream()
                .map(AppointmentBookedResource::new).collect(Collectors.toList());
    }
}
