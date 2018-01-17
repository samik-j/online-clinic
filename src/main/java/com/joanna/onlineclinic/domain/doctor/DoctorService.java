package com.joanna.onlineclinic.domain.doctor;

public class DoctorService {

    private DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }
}
