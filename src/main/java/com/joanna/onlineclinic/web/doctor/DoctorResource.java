package com.joanna.onlineclinic.web.doctor;

import com.joanna.onlineclinic.domain.doctor.Specialty;

public class DoctorResource {

    private long id;
    private String firstName;
    private String lastName;
    private Specialty specialty;

    public DoctorResource() {
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

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
}
