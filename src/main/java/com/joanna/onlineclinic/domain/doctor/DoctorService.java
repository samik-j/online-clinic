package com.joanna.onlineclinic.domain.doctor;

import com.joanna.onlineclinic.web.doctor.DoctorResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor registerDoctor(DoctorResource resource) {
        Doctor doctor = new Doctor
                (resource.getFirstName(), resource.getLastName(), resource.getSpecialty());

        return doctorRepository.save(doctor);
    }

    public List<Doctor> findDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> findDoctors(Specialty specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    public Doctor findDoctorById(long id) {
        return doctorRepository.findOne(id);
    }

    public boolean doctorExists(long doctorId) {
        return doctorRepository.exists(doctorId);
    }
}
