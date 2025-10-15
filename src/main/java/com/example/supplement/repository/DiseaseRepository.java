package com.example.supplement.repository;

import com.example.supplement.entity.Disease;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DiseaseRepository {
    private final List<Disease> diseases = new ArrayList<>();

    public Disease save(Disease disease) {
        diseases.removeIf(existing -> existing.getId().equals(disease.getId()));
        diseases.add(disease);
        return disease;
    }

    public Optional<Disease> findById(UUID id) {
        return diseases.stream()
            .filter(disease -> disease.getId().equals(id))
            .findFirst();
    }

    public List<Disease> findAll() {
        return new ArrayList<>(diseases);
    }
}
