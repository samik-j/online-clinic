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
        Patient patient = new Patient.PatientBuilder()
                .firstName(resource.getFirstName())
                .lastName(resource.getLastName())
                .nhsNumber(resource.getNhsNumber())
                .phoneNumber(resource.getPhoneNumber())
                .email(resource.getEmail())
                .build();

        return repository.save(patient);
    }

    public List<Patient> findAll() {
        return repository.findAll();
    }

    public boolean patientExists(long patientId) {
        return repository.exists(patientId);
    }
}
