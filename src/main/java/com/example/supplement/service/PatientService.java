package com.example.supplement.service;

import com.example.supplement.entity.Disease;
import com.example.supplement.entity.Patient;
import com.example.supplement.entity.Supplement;
import com.example.supplement.repository.DiseaseRepository;
import com.example.supplement.repository.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PatientService {
    private final PatientRepository patientRepository;
    private final DiseaseRepository diseaseRepository;

    public PatientService(PatientRepository patientRepository, DiseaseRepository diseaseRepository) {
        this.patientRepository = patientRepository;
        this.diseaseRepository = diseaseRepository;
    }

    public Patient registerPatient(String name, int age, List<UUID> diseaseIds) {
        List<Disease> diseases = diseaseIds.stream()
            .map(id -> diseaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disease not found: " + id)))
            .collect(Collectors.toList());
        Patient patient = new Patient(name, age, diseases);
        patientRepository.save(patient);
        return patient;
    }

    public Optional<Patient> findById(UUID id) {
        return patientRepository.findById(id);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public void addDiseaseToPatient(UUID patientId, UUID diseaseId) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
        Disease disease = diseaseRepository.findById(diseaseId)
            .orElseThrow(() -> new IllegalArgumentException("Disease not found: " + diseaseId));
        patient.addDisease(disease);
        patientRepository.save(patient);
    }

    public void addAcceptedSupplement(UUID patientId, Supplement supplement) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
        patient.addSupplement(supplement);
        patientRepository.save(patient);
    }

    public List<Supplement> getCurrentSupplements(UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
        return patient.getCurrentSupplements();
    }
}
