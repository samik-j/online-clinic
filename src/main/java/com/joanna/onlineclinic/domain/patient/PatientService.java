package com.joanna.onlineclinic.domain.patient;

import com.joanna.onlineclinic.web.patient.PatientResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public boolean nhsNumberExists(String nhsNumber) {
        return repository.existsByNhsNumber(nhsNumber);
    }

    public Patient registerPatient(PatientResource resource) {
        Patient patient = new Patient(resource.getFirstName(), resource.getLastName(),
                resource.getNhsNumber(), resource.getPhoneNumber(), resource.getEmail());

        return repository.save(patient);
    }

    public List<Patient> findAll() {
        return repository.findAll();
    }
}
