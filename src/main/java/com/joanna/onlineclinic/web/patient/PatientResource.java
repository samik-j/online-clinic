package com.joanna.onlineclinic.web.patient;

import com.joanna.onlineclinic.domain.patient.Patient;

public class PatientResource {

    private long id;
    private String firstName;
    private String lastName;
    private String nhsNumber;
    private String phoneNumber;
    private String email;

    public PatientResource() {
    }

    PatientResource(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.nhsNumber = patient.getNhsNumber();
        this.phoneNumber = patient.getPhoneNumber();
        this.email = patient.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
