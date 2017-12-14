package com.joanna.onlineclinic.domain.doctor;

import com.joanna.onlineclinic.domain.appointment.AppointmentAvailable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private Set<AppointmentAvailable> appointmentsAvailable;

    Doctor() {
    }

    public Doctor(String firstName, String lastName, Specialty specialty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.appointmentsAvailable = new HashSet<>();
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

    public Specialty getSpecialty() {
        return specialty;
    }

    public Set<AppointmentAvailable> getAppointmentsAvailable() {
        return appointmentsAvailable;
    }
}
