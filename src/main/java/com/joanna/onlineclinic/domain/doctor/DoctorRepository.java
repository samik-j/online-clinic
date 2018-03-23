package com.joanna.onlineclinic.domain.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecialty(Specialty specialty);

    boolean existsByEmail(String email);
}
