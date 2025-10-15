package com.example.supplement.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Supplement {
    private final UUID id;
    private String name;
    private final List<String> activeIngredients;

    public Supplement(String name, List<String> activeIngredients) {
        this(UUID.randomUUID(), name, activeIngredients);
    }

    public Supplement(UUID id, String name, List<String> activeIngredients) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.activeIngredients = new ArrayList<>(Objects.requireNonNull(activeIngredients, "activeIngredients"));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public List<String> getActiveIngredients() {
        return Collections.unmodifiableList(activeIngredients);
    }

    public void setActiveIngredients(List<String> newIngredients) {
        activeIngredients.clear();
        activeIngredients.addAll(Objects.requireNonNull(newIngredients, "newIngredients"));
    }

    @Override
    public String toString() {
        return "Supplement{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", activeIngredients=" + activeIngredients +
            '}';
    }
}
