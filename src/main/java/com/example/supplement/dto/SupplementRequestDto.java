package com.example.supplement.dto;

import java.util.List;
import java.util.Objects;

public class SupplementRequestDto {
    private final String name;
    private final List<String> activeIngredients;

    public SupplementRequestDto(String name, List<String> activeIngredients) {
        this.name = Objects.requireNonNull(name, "name");
        this.activeIngredients = List.copyOf(Objects.requireNonNull(activeIngredients, "activeIngredients"));
    }

    public String getName() {
        return name;
    }

    public List<String> getActiveIngredients() {
        return activeIngredients;
    }
}
