package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}
