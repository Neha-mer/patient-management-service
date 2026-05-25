package com.nm.patientservice.mapper;

import com.nm.patientservice.dto.PatientResponseDTO;
import com.nm.patientservice.dto.PatientRquestDTO;
import com.nm.patientservice.model.Patient;

import java.time.LocalDate;


public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();

        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDTO;
    }

    public static Patient rquestDTO(PatientRquestDTO patientRquestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRquestDTO.getName());
        patient.setEmail(patientRquestDTO.getEmail());
        patient.setAddress(patientRquestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRquestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRquestDTO.getRegisteredDate()));

        return patient;
    }
}
