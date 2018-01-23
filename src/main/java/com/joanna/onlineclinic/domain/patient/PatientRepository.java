package com.joanna.onlineclinic.domain.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByNhsNumber(@Param("NHSNumber")String nhsNumber);
}
