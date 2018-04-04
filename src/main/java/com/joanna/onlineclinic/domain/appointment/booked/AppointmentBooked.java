package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.BaseEntity;
import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.IncorrectObjectStateException;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.patient.Patient;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"appointment_id", "doctor_id", "patient_id"}))
public class AppointmentBooked implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private String reason;
    @Enumerated(EnumType.STRING)
    private AppointmentBookedStatus status = AppointmentBookedStatus.NOT_CONFIRMED;

    AppointmentBooked() {
    }

    public AppointmentBooked(Appointment appointment, Patient patient, String reason) {
        this.appointment = appointment;
        this.doctor = appointment.getDoctor();
        this.patient = patient;
        this.reason = reason;
        this.status = AppointmentBookedStatus.NOT_CONFIRMED;
    }

    public long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getReason() {
        return reason;
    }

    public AppointmentBookedStatus getStatus() {
        return status;
    }

    void changeStatus(AppointmentBookedStatus status) {
        if (!this.status.equals(status)) {
            this.status = status;
        } else {
            throw new IncorrectObjectStateException("Status is the same");
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

        AppointmentBooked that = (AppointmentBooked) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
