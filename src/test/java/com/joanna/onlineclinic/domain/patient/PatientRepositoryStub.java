package com.joanna.onlineclinic.domain.patient;


import com.joanna.onlineclinic.domain.AbstractRepositoryStub;

public class PatientRepositoryStub extends AbstractRepositoryStub<Patient> implements PatientRepository {

    @Override
    public boolean existsByNhsNumber(String nhsNumber) {
        return store.values().stream()
            .anyMatch(patient -> patient.getNhsNumber().equals(nhsNumber));
    }

}
