package com.joanna.onlineclinic.domain.doctor;

import com.joanna.onlineclinic.web.doctor.DoctorResource;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor registerDoctor(DoctorResource resource) {
        Doctor doctor = new Doctor
                (resource.getFirstName(), resource.getLastName(), Specialty.valueOf(resource.getSpecialty()));

        return repository.save(doctor);
    }
}
