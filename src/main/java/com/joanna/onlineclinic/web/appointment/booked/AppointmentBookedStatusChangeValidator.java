package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedService;
import com.joanna.onlineclinic.web.ErrorsResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentBookedStatusChangeValidator {

    private AppointmentBookedService appointmentBookedService;

    public AppointmentBookedStatusChangeValidator(
            AppointmentBookedService appointmentBookedService) {
        this.appointmentBookedService = appointmentBookedService;
    }

    public ErrorsResource validate(
            long appointmentId, AppointmentBookedStatusChangeResource resource) {
        List<String> validationErrors = new ArrayList<>();

        if (!isStatusChangePossible(appointmentId, resource)) {
            validationErrors.add("Status already changed");
        }

        return new ErrorsResource(validationErrors);
    }

    private boolean isStatusChangePossible(
            long appointmentId, AppointmentBookedStatusChangeResource resource) {
        return !appointmentBookedService.findById(appointmentId).getStatus()
                .equals(resource.getStatus());
    }
}
