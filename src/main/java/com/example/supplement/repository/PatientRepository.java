package com.example.supplement.repository;

import com.example.supplement.entity.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    public Patient save(Patient patient) {
        patients.removeIf(existing -> existing.getId().equals(patient.getId()));
        patients.add(patient);
        return patient;
    }

    public Optional<Patient> findById(UUID id) {
        return patients.stream()
            .filter(patient -> patient.getId().equals(id))
            .findFirst();
    }

    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }
}
