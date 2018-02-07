package com.joanna.onlineclinic.domain.patient;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import org.apache.commons.lang3.Validate;

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
    private String nhsNumber;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private Set<AppointmentBooked> appointments;

    Patient() {
    }

//    public Patient(
//            String firstName, String lastName, String nhsNumber, String phoneNumber, String email) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.nhsNumber = nhsNumber;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.appointments = new HashSet<>();
//    }

    public Patient(PatientBuilder builder) {
        this.firstName = Validate.notBlank(builder.firstName);
        this.lastName = Validate.notBlank(builder.lastName);
        this.nhsNumber = builder.nhsNumber;
        this.phoneNumber = Validate.notBlank(builder.phoneNumber);
        this.email = Validate.notBlank(builder.email);
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

    public String getNhsNumber() {
        return nhsNumber;
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

    public void addAppointmentBooked(AppointmentBooked appointment) {
        appointments.add(appointment);
    }

    public static class PatientBuilder {

        private String firstName;
        private String lastName;
        private String nhsNumber;
        private String phoneNumber;
        private String email;

        public PatientBuilder() {
        }

        public PatientBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PatientBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PatientBuilder nhsNumber(String nhsNumber) {
            this.nhsNumber = nhsNumber;
            return this;
        }

        public PatientBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PatientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }
}
