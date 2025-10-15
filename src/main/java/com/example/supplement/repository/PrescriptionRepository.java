package com.example.supplement.repository;

import com.example.supplement.entity.Prescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PrescriptionRepository {
    private final List<Prescription> prescriptions = new ArrayList<>();

    public Prescription save(Prescription prescription) {
        prescriptions.removeIf(existing -> existing.getId().equals(prescription.getId()));
        prescriptions.add(prescription);
        return prescription;
    }

    public List<Prescription> findAll() {
        return new ArrayList<>(prescriptions);
    }

    public Optional<Prescription> findById(UUID id) {
        return prescriptions.stream()
            .filter(prescription -> prescription.getId().equals(id))
            .findFirst();
    }

    public List<Prescription> findByPatientId(UUID patientId) {
        return prescriptions.stream()
            .filter(prescription -> prescription.getPatient().getId().equals(patientId))
            .collect(Collectors.toList());
    }
}
