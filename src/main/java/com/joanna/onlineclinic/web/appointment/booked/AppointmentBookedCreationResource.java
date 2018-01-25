package com.joanna.onlineclinic.web.appointment.booked;

public class AppointmentBookedCreationResource {

    private Long appointmentAvailableId;
    private Long doctorId;
    private Long patientId;
    private String reason;

    public AppointmentBookedCreationResource() {
    }

    public Long getAppointmentAvailableId() {
        return appointmentAvailableId;
    }

    public void setAppointmentAvailableId(long appointmentAvailableId) {
        this.appointmentAvailableId = appointmentAvailableId;
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
