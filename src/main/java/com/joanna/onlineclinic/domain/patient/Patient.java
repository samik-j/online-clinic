package com.joanna.onlineclinic.domain.patient;

import javax.persistence.*;

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
    private String NHSnumber;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;

    Patient() {
    }

    public Patient(String firstName, String lastName, String NHSnumber, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.NHSnumber = NHSnumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public String getNHSnumber() {
        return NHSnumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
