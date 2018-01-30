package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedService;
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
    public ResponseEntity<Object> addAppointmentBooked(
            @RequestBody AppointmentBookedCreationResource resource) {
        LOGGER.info("Appointment booked: appointment id: {}, patient id: {}, reason: {}",
                resource.getAppointmentId(), resource.getPatientId(), resource.getReason());

        ErrorsResource errorsResource = validator.validate(resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            return new ResponseEntity<Object>(
                    getAppointmentBookedResource(service.registerAppointment(resource)), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(
                    errorsResource.getValidationErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    private AppointmentBookedResource getAppointmentBookedResource(AppointmentBooked appointment) {
        return new AppointmentBookedResource(appointment);
    }
}
