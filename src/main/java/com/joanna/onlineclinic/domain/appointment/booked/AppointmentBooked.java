package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.patient.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"doctor_id", "appointmentDateTime", "patient_id"}))
public class AppointmentBooked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private String reason;
    private AppointmentBookedStatus status;

    AppointmentBooked() {
    }

    public AppointmentBooked(
            Doctor doctor, LocalDateTime appointmentDateTime, Patient patient, String reason) {
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
        this.patient = patient;
        this.reason = reason;
        this.status = AppointmentBookedStatus.NOT_CONFIRMED;
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

    public AppointmentBookedStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppointmentBooked that = (AppointmentBooked) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
