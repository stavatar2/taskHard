package com.example.supplement.service;

import com.example.supplement.entity.Disease;
import com.example.supplement.entity.Supplement;
import com.example.supplement.repository.DiseaseRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DiseaseService {
    private final DiseaseRepository repository;

    public DiseaseService(DiseaseRepository repository) {
        this.repository = repository;
    }

    public Disease createDisease(String name, Set<String> prohibitedSubstances) {
        Disease disease = new Disease(name, prohibitedSubstances);
        repository.save(disease);
        return disease;
    }

    public Optional<Disease> findById(UUID id) {
        return repository.findById(id);
    }

    public List<Disease> findAll() {
        return repository.findAll();
    }

    public Set<String> findConflictingSubstances(Supplement supplement, List<Disease> diseases) {
        Set<String> active = new HashSet<>(supplement.getActiveIngredients());
        return diseases.stream()
            .flatMap(disease -> disease.getProhibitedSubstances().stream())
            .filter(active::contains)
            .collect(Collectors.toSet());
    }
}
