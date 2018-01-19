package com.joanna.onlineclinic.web.appointment.available;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joanna.onlineclinic.domain.appointment.available.AppointmentAvailable;

import java.time.LocalDateTime;

public class AppointmentAvailableResource { // mogloby byc osobne do creation i miec tylko date i drugie to get ktore juz ma wszystko

    private long id;
    private long doctorId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentDateTime;

    public AppointmentAvailableResource() {
    }

    AppointmentAvailableResource(AppointmentAvailable appointment) {
        this.id = appointment.getId();
        this.doctorId = appointment.getDoctor().getId();
        this.appointmentDateTime = appointment.getAppointmentDateTime();
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
