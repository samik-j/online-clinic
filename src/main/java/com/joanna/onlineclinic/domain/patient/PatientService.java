package com.joanna.onlineclinic.domain.patient;

import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public boolean nhsNumberExists(String nhsNumber) {
        return repository.existsByNhsNumber(nhsNumber);
    }
}
