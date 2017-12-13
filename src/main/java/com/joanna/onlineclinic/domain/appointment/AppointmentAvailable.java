package com.joanna.onlineclinic.domain.appointment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"doctorId", "dateTime"}))
public class AppointmentAvailable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long doctorId;
    private LocalDateTime dateTime;

    AppointmentAvailable() {
    }

    public AppointmentAvailable(long doctorId, LocalDateTime dateTime) {
        this.doctorId = doctorId;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
