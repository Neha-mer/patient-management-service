package com.nm.patientservice.repository;

import com.nm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findPatientNameById(UUID uuid);
    boolean existsByEmailAndIdNot(String email, UUID id);
    boolean existsByEmail(String email);
}
