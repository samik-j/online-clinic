package com.joanna.onlineclinic.domain.doctor;

import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }
}
