package com.joanna.onlineclinic.domain.patient;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true)
    private String NHSNumber;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private Set<AppointmentBooked> appointments;

    Patient() {
    }

    public Patient(String firstName, String lastName, String NHSNumber, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.NHSNumber = NHSNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.appointments = new HashSet<>();
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

    public String getNHSNumber() {
        return NHSNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Set<AppointmentBooked> getAppointments() {
        return appointments;
    }
}
