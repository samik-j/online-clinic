package com.joanna.onlineclinic.web.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResource {

    private long id;
    private long doctorId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private boolean available;
    private Long appointmentBookedId;

    public AppointmentResource() {
    }

    AppointmentResource(Appointment appointment) {
        this.id = appointment.getId();
        this.doctorId = appointment.getDoctor().getId();
        this.date = appointment.getDate();
        this.time = appointment.getTime();
        this.available = appointment.isAvailable();
        appointment.getAppointmentsBooked().stream()
                   .filter(appt -> appt.getStatus() != AppointmentBookedStatus.CANCELLED)
                   .findFirst()
                   .ifPresent(appointmentBooked1 -> this.appointmentBookedId = appointmentBooked1.getId());
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getAppointmentBookedId() {
        return appointmentBookedId;
    }

    public void setAppointmentBookedId(Long appointmentBookedId) {
        this.appointmentBookedId = appointmentBookedId;
    }
}
