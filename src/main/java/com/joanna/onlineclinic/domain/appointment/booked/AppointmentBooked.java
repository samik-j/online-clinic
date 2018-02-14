package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.BaseEntity;
import com.joanna.onlineclinic.domain.appointment.IncorrectObjectStateException;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.patient.Patient;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"doctor_id", "date", "time", "patient_id"}))
public class AppointmentBooked implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime time;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private String reason;
    @Enumerated(EnumType.STRING)
    private AppointmentBookedStatus status = AppointmentBookedStatus.NOT_CONFIRMED;

    AppointmentBooked() {
    }

    public AppointmentBooked(
            Doctor doctor, LocalDate date, LocalTime time, Patient patient, String reason) {
        this.doctor = doctor;
        this.date = date;
        this.time = time;
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
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

    void changeStatus(AppointmentBookedStatus status) {
        if (!this.status.equals(status)) {
            this.status = status;
        } else {
            throw new IncorrectObjectStateException("Status is the same");
        }
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
