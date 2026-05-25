package com.nm.patientservice.controller;

import com.nm.patientservice.dto.PatientResponseDTO;
import com.nm.patientservice.dto.PatientRquestDTO;
import com.nm.patientservice.exception.EmailAlreadyExistError;
import com.nm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.javapoet.LordOfTheStrings;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.lang.invoke.CallSite;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    PatientService patientService;

    @GetMapping("/getAllPatients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients(){

        List<PatientResponseDTO> responseDTOS=  patientService.getAllPatients();
        for(PatientResponseDTO patientResponseDTO:responseDTOS) {
            System.out.println(patientResponseDTO.getName());
        }
        return ResponseEntity.ok().body(responseDTOS);

  }

  @GetMapping("/getName")

  public ResponseEntity<String> getPatientNane(@RequestParam UUID uuid){
       String name= patientService.getPatienName(uuid);
       return ResponseEntity.ok().body(name);

  }


  @PostMapping("/createPatient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRquestDTO patientRquestDTO) throws EmailAlreadyExistError {
        PatientResponseDTO patientResponseDTO=patientService.createPatient(patientRquestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
  }

  @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Validated(Default.class) @RequestBody PatientRquestDTO patientRquestDTO) {
        PatientResponseDTO patientResponseDTO=patientService.updatePatient(id,patientRquestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
  }

  @DeleteMapping("/{id}")
    public void  deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
    }


}
