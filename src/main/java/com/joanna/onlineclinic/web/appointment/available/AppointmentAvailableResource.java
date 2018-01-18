package com.joanna.onlineclinic.web.appointment.available;

import java.time.LocalDateTime;

public class AppointmentAvailableResource {

    private long id;
    private long doctorId;
    private LocalDateTime appointmentDateTime;

    public AppointmentAvailableResource() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }
}
