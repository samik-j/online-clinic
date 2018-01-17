package com.joanna.onlineclinic.domain.patient;

public class PatientService {

    private PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }
}
