package com.joanna.onlineclinic.web.appointment.booked;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentBookedResource {

    private long id;
    private long doctorId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private Long patientId;
    private String reason;
    private AppointmentBookedStatus status;

    public AppointmentBookedResource() {
    }

    AppointmentBookedResource(AppointmentBooked appointmentBooked) {
        this.id = appointmentBooked.getId();
        this.doctorId = appointmentBooked.getDoctor().getId();
        this.date = appointmentBooked.getDate();
        this.time = appointmentBooked.getTime();
        this.patientId = appointmentBooked.getPatient().getId();
        this.reason = appointmentBooked.getReason();
        this.status = appointmentBooked.getStatus();
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public AppointmentBookedStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentBookedStatus status) {
        this.status = status;
    }
}
