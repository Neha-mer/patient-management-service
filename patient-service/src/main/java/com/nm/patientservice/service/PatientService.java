package com.nm.patientservice.service;

import com.nm.patientservice.PatientServiceApplication;
import com.nm.patientservice.dto.PatientResponseDTO;
import com.nm.patientservice.dto.PatientRquestDTO;
import com.nm.patientservice.exception.EmailAlreadyExistError;
import com.nm.patientservice.exception.PatientNotFoundException;
import com.nm.patientservice.mapper.PatientMapper;
import com.nm.patientservice.model.Patient;
import com.nm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    private PatientResponseDTO patientResponseDTO;
    private PatientRquestDTO patientRquestDTO;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        this.patientResponseDTO = patientResponseDTO;
        this.patientRquestDTO = patientRquestDTO;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS = patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
        return patientResponseDTOS;
    }

    public String getPatienName(UUID uuid) {
        Optional<Patient> patient = patientRepository.findPatientNameById(uuid);
        return patient.map(Patient::getName).orElseThrow(() -> new RuntimeException("Patient Not found"));
    }

    public PatientResponseDTO createPatient(PatientRquestDTO patientRquestDTO) throws EmailAlreadyExistError{
      boolean existEmail= patientRepository.existsByEmail(patientRquestDTO.getEmail());
       if (existEmail) {
           throw new EmailAlreadyExistError("Email Already Exists");
       }
        Patient newPatient = PatientMapper.rquestDTO(patientRquestDTO);
        patientRepository.save(newPatient);
        return PatientMapper.toDTO(newPatient);

    }

   /* public PatientResponseDTO updatePatient(UUID uuid,PatientRquestDTO patientRquestDTO) {

        Optional<Patient> patient=patientRepository.findById(uuid).;
        if(patient.isPresent()) {
            patient.get().setName(patientRquestDTO.getName());
            patient.get().setEmail(patientRquestDTO.getEmail());
            patient.get().setAddress(patientRquestDTO.getAddress());
            patient.get().setDateOfBirth(LocalDate.parse(patientRquestDTO.getDateOfBirth()));
            patientRepository.save(patient.get());

        }

        return PatientMapper.toDTO(patient.get());

    }*/
   public PatientResponseDTO updatePatient(UUID id,
                                           PatientRquestDTO patientRequestDTO) {

       Patient patient = patientRepository.findById(id).orElseThrow(
               () -> new PatientNotFoundException("Patient not found with ID: " + id));

       if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),
               id)) {
           throw new EmailAlreadyExistError(
                   "A patient with this email " + "already exists"
                           + patientRequestDTO.getEmail());
       }

       patient.setName(patientRequestDTO.getName());
       patient.setAddress(patientRequestDTO.getAddress());
       patient.setEmail(patientRequestDTO.getEmail());
       patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

       Patient updatedPatient = patientRepository.save(patient);
       return PatientMapper.toDTO(updatedPatient);
   }

   public void deletePatient(UUID id) {
       patientRepository.deleteById(id);
   }
}

