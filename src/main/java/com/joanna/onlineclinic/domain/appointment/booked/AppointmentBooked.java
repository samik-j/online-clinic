package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.patient.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"doctor", "appointmentDateTime", "patient"}))
public class AppointmentBooked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @Column(nullable = false)
    private Doctor doctor;
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @Column(nullable = false)
    private Patient patient;
    private String reason;
    private boolean confirmed;

    AppointmentBooked() {
    }

    public AppointmentBooked(Doctor doctor, LocalDateTime appointmentDateTime, Patient patient, String reason) {
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
        this.patient = patient;
        this.reason = reason;
        this.confirmed = false;
    }

    public long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getReason() {
        return reason;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
