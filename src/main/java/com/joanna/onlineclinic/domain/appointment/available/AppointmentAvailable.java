package com.joanna.onlineclinic.domain.appointment.available;

import com.joanna.onlineclinic.domain.doctor.Doctor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"doctor_id", "appointmentDateTime"}))
public class AppointmentAvailable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    AppointmentAvailable() {
    }

    public AppointmentAvailable(Doctor doctor, LocalDateTime appointmentDateTime) {
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppointmentAvailable that = (AppointmentAvailable) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
