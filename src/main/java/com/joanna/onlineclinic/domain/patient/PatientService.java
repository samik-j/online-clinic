package com.joanna.onlineclinic.domain.patient;

import com.joanna.onlineclinic.web.patient.PatientResource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository repository;

    public PatientService(PatientRepository patientRepository) {
        this.repository = patientRepository;
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

    public Patient findById(long id) {
        return repository.findOne(id);
    }

    public boolean existsById(long patientId) {
        return repository.exists(patientId);
    }

    public boolean existsByNhsNumber(String nhsNumber) {
        return repository.existsByNhsNumber(nhsNumber);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
