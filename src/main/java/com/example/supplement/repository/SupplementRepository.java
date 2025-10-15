package com.example.supplement.repository;

import com.example.supplement.entity.Supplement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SupplementRepository {
    private final List<Supplement> supplements = new ArrayList<>();

    public Supplement save(Supplement supplement) {
        supplements.removeIf(existing -> existing.getId().equals(supplement.getId()));
        supplements.add(supplement);
        return supplement;
    }

    public Optional<Supplement> findById(UUID id) {
        return supplements.stream()
            .filter(supplement -> supplement.getId().equals(id))
            .findFirst();
    }

    public List<Supplement> findAll() {
        return new ArrayList<>(supplements);
    }
}
