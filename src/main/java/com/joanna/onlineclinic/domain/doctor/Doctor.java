package com.joanna.onlineclinic.domain.doctor;

import com.joanna.onlineclinic.domain.BaseEntity;
import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Doctor implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private Set<Appointment> appointments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private Set<AppointmentBooked> appointmentsBooked;

    Doctor() {
    }

    public Doctor(String firstName, String lastName, String email, Specialty specialty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialty = specialty;
        this.appointments = new HashSet<>();
        this.appointmentsBooked = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Set<AppointmentBooked> getAppointmentsBooked() {
        return appointmentsBooked;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void addAppointmentBooked(AppointmentBooked appointment) {
        appointmentsBooked.add(appointment);
    }
}
