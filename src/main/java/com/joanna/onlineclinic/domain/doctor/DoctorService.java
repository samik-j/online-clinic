package com.joanna.onlineclinic.domain.doctor;

import com.joanna.onlineclinic.web.doctor.DoctorResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor registerDoctor(DoctorResource resource) {
        Doctor doctor = new Doctor
                (resource.getFirstName(), resource.getLastName(), resource.getSpecialty());

        return repository.save(doctor);
    }

    public List<Doctor> findDoctors() {
        return repository.findAll();
    }

    public List<Doctor> findDoctors(Specialty specialty) {
        return repository.findBySpecialty(specialty);
    }
}
