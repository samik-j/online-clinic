package com.joanna.onlineclinic.web.appointment.booked;

public class AppointmentBookedCreationResource {

    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private String reason;

    public AppointmentBookedCreationResource() {
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
