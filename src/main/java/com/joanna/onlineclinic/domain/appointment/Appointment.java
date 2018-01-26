package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.doctor.Doctor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"doctor_id", "appointmentDateTime"}))
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;
    private boolean available;

    Appointment() {
    }

    public Appointment(Doctor doctor, LocalDateTime appointmentDateTime) {
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
        this.available = true;
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

    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Appointment that = (Appointment) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
