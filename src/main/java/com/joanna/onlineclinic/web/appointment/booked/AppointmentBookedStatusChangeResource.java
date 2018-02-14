package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedStatus;

public class AppointmentBookedStatusChangeResource {

    private AppointmentBookedStatus status;

    public AppointmentBookedStatusChangeResource() {
    }

    public AppointmentBookedStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentBookedStatus status) {
        this.status = status;
    }
}
