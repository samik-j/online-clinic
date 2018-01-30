package com.joanna.onlineclinic.web.appointment.booked;

public class AppointmentBookedCreationResource {

    private Long appointmentId;
    private Long patientId;
    private String reason;

    public AppointmentBookedCreationResource() {
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
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
}
