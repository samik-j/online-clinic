package com.joanna.onlineclinic.domain.appointment;

import com.joanna.onlineclinic.domain.BaseEntity;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import com.joanna.onlineclinic.domain.doctor.Doctor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"doctor_id", "date", "time"})})
public class Appointment implements BaseEntity {

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
    private boolean available;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appointment")
    private Set<AppointmentBooked> appointmentsBooked;

    Appointment() {
    }

    public Appointment(Doctor doctor, LocalDate date, LocalTime time) {
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.available = true;
        this.appointmentsBooked = new HashSet<>();
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

    public boolean isAvailable() {
        return available;
    }

    public Set<AppointmentBooked> getAppointmentsBooked() {
        return appointmentsBooked;
    }

    public void book(AppointmentBooked appointmentBooked) {
        if (available) {
            available = false;
            appointmentsBooked.add(appointmentBooked);
        } else {
            throw new IncorrectObjectStateException("Appointment is not available");
        }
    }

    public void cancel() {
        if (!available) {
            available = true;
        } else {
            throw new IncorrectObjectStateException("Appointment is available");
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

        Appointment that = (Appointment) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
