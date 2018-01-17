package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.Doctor;

public class DoctorResource {

    private long id;
    private String firstName;
    private String lastName;
    private String specialty;

    public DoctorResource() {
    }

    DoctorResource(Doctor doctor) {
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.specialty = doctor.getSpecialty().toString();
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
