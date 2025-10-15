package com.example.supplement.dto;

import java.util.List;
import java.util.UUID;

public class SupplementResponseDto {
    private final UUID id;
    private final String name;
    private final List<String> activeIngredients;

    public SupplementResponseDto(UUID id, String name, List<String> activeIngredients) {
        this.id = id;
        this.name = name;
        this.activeIngredients = activeIngredients;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getActiveIngredients() {
        return activeIngredients;
    }
}
