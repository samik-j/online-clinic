package com.joanna.onlineclinic.domain.doctor;

import com.joanna.onlineclinic.domain.AbstractRepositoryStub;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorRepositoryStub
        extends AbstractRepositoryStub<Doctor> implements DoctorRepository {

    @Override
    public List<Doctor> findBySpecialty(Specialty specialty) {
        return store.values().stream()
                .filter(doctor -> doctor.getSpecialty().equals(specialty))
                .collect(Collectors.toList());
    }
}
