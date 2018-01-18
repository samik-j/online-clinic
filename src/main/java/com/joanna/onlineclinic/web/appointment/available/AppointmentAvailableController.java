package com.joanna.onlineclinic.web.appointment.available;

import com.joanna.onlineclinic.domain.appointment.available.AppointmentAvailableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppointmentAvailableController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentAvailableController.class);
    private AppointmentAvailableService appointmentService;

    public AppointmentAvailableController(AppointmentAvailableService appointmentService) {
        this.appointmentService = appointmentService;
    }
}
