package com.joanna.onlineclinic.web.appointment.booked;

public class AppointmentBookedCreationResource {

    private long appointmentAvailableId;
    private long doctorId;
    private long patientId;

    public AppointmentBookedCreationResource() {
    }

    public long getAppointmentAvailableId() {
        return appointmentAvailableId;
    }

    public void setAppointmentAvailableId(long appointmentAvailableId) {
        this.appointmentAvailableId = appointmentAvailableId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }
}
